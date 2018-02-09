/**
 *  Copyright (c) 2015-2018 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.tm4e.samples.typescript;

import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.registry.Registry;
import org.eclipse.tm4e.ui.text.TMPresentationReconciler;

public class TypeScriptViewerConfiguration extends SourceViewerConfiguration {

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer viewer) {
		// Defines a TextMate Presentation reconcilier
		TMPresentationReconciler reconciler = new TMPresentationReconciler();
		// Set the TypeScript grammar
		reconciler.setGrammar(getGrammar());
		//reconciler.setThemeId(ThemeIdConstants.Monokai);
		return reconciler;
	}

	private IGrammar getGrammar() {
		// TODO: cache the grammar
		Registry registry = new Registry();
		try {
			return registry.loadGrammarFromPathSync("TypeScript.tmLanguage.json",
					TypeScriptViewerConfiguration.class.getResourceAsStream("TypeScript.tmLanguage.json"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
