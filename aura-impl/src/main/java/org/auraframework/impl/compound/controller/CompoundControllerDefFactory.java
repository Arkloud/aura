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
package org.auraframework.impl.compound.controller;

import com.google.common.collect.Maps;
import org.auraframework.Aura;
import org.auraframework.def.ActionDef;
import org.auraframework.def.ApplicationDef;
import org.auraframework.def.BaseComponentDef;
import org.auraframework.def.ComponentDef;
import org.auraframework.def.ControllerDef;
import org.auraframework.def.DefDescriptor;
import org.auraframework.impl.DefinitionAccessImpl;
import org.auraframework.impl.compound.controller.CompoundControllerDef.Builder;
import org.auraframework.impl.system.DefDescriptorImpl;
import org.auraframework.impl.system.DefFactoryImpl;
import org.auraframework.system.AuraContext;
import org.auraframework.throwable.quickfix.QuickFixException;

import java.util.Map;

/**
 */
public class CompoundControllerDefFactory extends DefFactoryImpl<ControllerDef> {

    @Override
    public ControllerDef getDef(DefDescriptor<ControllerDef> descriptor) throws QuickFixException {
        Builder builder = new Builder();
        builder.setDescriptor(descriptor);
        builder.setLocation(descriptor.getQualifiedName(), -1);

        DefDescriptor<ComponentDef> compDesc = DefDescriptorImpl.getAssociateDescriptor(descriptor, ComponentDef.class,
                DefDescriptor.MARKUP_PREFIX);
        BaseComponentDef componentDef = null;
        if (compDesc.exists()) {
            componentDef = Aura.getDefinitionService().getDefinition(compDesc);
        } else {
            DefDescriptor<ApplicationDef> appDesc = DefDescriptorImpl.getAssociateDescriptor(descriptor,
                    ApplicationDef.class, DefDescriptor.MARKUP_PREFIX);
            componentDef = Aura.getDefinitionService().getDefinition(appDesc);
        }

        if (componentDef == null) {
            DefDescriptor<ComponentDef> layoutDesc = DefDescriptorImpl.getAssociateDescriptor(descriptor,
                    ComponentDef.class, "layout");
            componentDef = Aura.getDefinitionService().getDefinition(layoutDesc);
        }

        Map<String, ActionDef> flattened = Maps.newHashMap();

        for (DefDescriptor<ControllerDef> delegate : componentDef.getControllerDefDescriptors()) {
            ControllerDef c = Aura.getDefinitionService().getDefinition(delegate);
            for (Map.Entry<String, ? extends ActionDef> e : c.getActionDefs().entrySet()) {
                ActionDef a = flattened.get(e.getKey());
                if (a != null) {
                    // TODO: server and client actions by same name, map needs
                    // key on action type
                } else {
                    flattened.put(e.getKey(), e.getValue());
                }
            }
        }
        builder.setActionDefs(flattened);
        builder.setAccess(new DefinitionAccessImpl(AuraContext.Access.PUBLIC));

        return builder.build();
    }
}
