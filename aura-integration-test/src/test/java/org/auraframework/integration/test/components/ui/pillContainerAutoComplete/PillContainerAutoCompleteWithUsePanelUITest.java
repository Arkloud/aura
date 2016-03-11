/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.integration.test.components.ui.pillContainerAutoComplete;

import org.auraframework.integration.test.util.WebDriverTestCase.TargetBrowsers;
import org.auraframework.test.util.WebDriverUtil.BrowserType;

@TargetBrowsers({ BrowserType.GOOGLECHROME, BrowserType.FIREFOX })
public class PillContainerAutoCompleteWithUsePanelUITest extends BasePillContainerAutoComplete{

	public PillContainerAutoCompleteWithUsePanelUITest() {
		super("/uitest/pillContainer_WithAutoCompleteUsePanelSet.cmp");	
	}
	@Override
	public void testLossOfFocusVerification(){
		//TODO:Remove this override test once W-2707857 is fixed
	}
}
