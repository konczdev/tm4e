/**
 * Copyright (c) 2018 Red Hat Inc. and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Bullen (Red Hat Inc.) - initial API and implementation
 */
package org.eclipse.tm4e.languageconfiguration.internal.model;

/**
 * Describes folding rules for a language.
 *
 * @see <a href=
 *      "https://github.com/microsoft/vscode/blob/8e2ec5a7ee1ae5500c645c05145359f2a814611c/src/vs/editor/common/languages/languageConfiguration.ts#L139">
 *      github.com/microsoft/vscode/blob/main/src/vs/editor/common/languages/languageConfiguration.ts#L139</a>
 */
public final class FoldingRules {

	/**
	 * Used by the indentation based strategy to decide whether empty lines belong to the previous or the next block.
	 * A language adheres to the off-side rule if blocks in that language are expressed by their indentation.
	 * See [wikipedia](https://en.wikipedia.org/wiki/Off-side_rule) for more information.
	 */
	public final boolean offSide;
	public final String markersStart;
	public final String markersEnd;

	public FoldingRules(final boolean offSide, final String markersStart, final String markersEnd) {
		this.offSide = offSide;
		this.markersStart = markersStart;
		this.markersEnd = markersEnd;
	}
}