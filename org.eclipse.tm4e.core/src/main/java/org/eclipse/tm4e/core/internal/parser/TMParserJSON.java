/**
 * Copyright (c) 2023 Vegard IT GmbH and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Sebastian Thomschke (Vegard IT) - initial implementation
 */
package org.eclipse.tm4e.core.internal.parser;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.Gson;

public class TMParserJSON implements TMParser {

	public final static TMParserJSON INSTANCE = new TMParserJSON();

	private static final Gson LOADER = new Gson();

	protected Map<String, Object> loadRaw(final Reader source) {
		return LOADER.fromJson(source, Map.class);
	}

	@Override
	public final <T extends PropertySettable<?>> T parse(final Reader source, final ObjectFactory<T> factory) throws Exception {
		final Map<String, Object> rawRoot = loadRaw(source);
		return transform(rawRoot, factory);
	}

	private <T extends PropertySettable<?>> T transform(final Map<String, Object> rawRoot, final ObjectFactory<T> factory) {
		final var root = factory.createRoot();
		final var path = new TMParserPropertyPath();

		for (final var e : rawRoot.entrySet()) {
			addChild(factory, path, root, e.getKey(), e.getValue());
		}
		return root;
	}

	/**
	 * @param propertyId String | Integer
	 */
	private <T extends PropertySettable<?>> void addChild(final ObjectFactory<T> handler, final TMParserPropertyPath path,
			final PropertySettable<?> parent, final Object propertyId, final Object unmappedChild) {
		path.add(propertyId);
		if (unmappedChild instanceof final Map<?, ?> map) {
			final var mappedChild = handler.createChild(path, unmappedChild.getClass());
			for (final Map.Entry<@NonNull ?, @NonNull ?> e : map.entrySet()) {
				addChild(handler, path, mappedChild, e.getKey(), e.getValue());
			}
			setProperty(parent, propertyId, mappedChild);
		} else if (unmappedChild instanceof final List list) {
			final var mappedChild = handler.createChild(path, unmappedChild.getClass());
			for (int i = 0, l = list.size(); i < l; i++) {
				addChild(handler, path, mappedChild, i, list.get(i));
			}
			setProperty(parent, propertyId, mappedChild);
		} else {
			setProperty(parent, propertyId, unmappedChild);
		}
		path.removeLast();
	}

	/**
	 * @param propertyId String | Integer
	 */
	@SuppressWarnings("unchecked")
	private void setProperty(final PropertySettable<?> settable, final Object propertyId, final Object value) {
		((PropertySettable<Object>) settable).setProperty(propertyId.toString(), value);
	}
}
