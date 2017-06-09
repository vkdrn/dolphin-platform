/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opendolphin.core.server.action;

import org.opendolphin.core.comm.ValueChangedCommand;
import org.opendolphin.core.server.ServerAttribute;
import org.opendolphin.core.server.comm.ActionRegistry;
import org.opendolphin.core.server.comm.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class StoreValueChangeAction extends DolphinServerAction {

    private static final Logger LOG = LoggerFactory.getLogger(StoreValueChangeAction.class);

    public void registerIn(final ActionRegistry registry) {
        registry.register(ValueChangedCommand.class, new CommandHandler<ValueChangedCommand>() {
            @Override
            public void handleCommand(final ValueChangedCommand command, final List response) {
                final ServerAttribute attribute = getServerModelStore().findAttributeById(command.getAttributeId());
                if (attribute != null) {
                    if (! Objects.equals(attribute.getValue(), command.getOldValue())) {
                        LOG.warn("S: updating attribute with id '{}' to new value '{}' even though its old command value '{}' does not conform to the old value of '{}'. Client overrules server.", command.getAttributeId(), command.getNewValue(), command.getOldValue(), attribute.getValue());
                    }

                    attribute.silently(new Runnable() {
                        @Override
                        public void run() {
                            attribute.setValue(command.getNewValue());
                        }

                    });
                } else {
                    LOG.error("S: cannot find attribute with id '{}' to change value from '{}' to '{}'.", command.getAttributeId(), command.getOldValue(), command.getNewValue());
                }
            }
        });
    }
}
