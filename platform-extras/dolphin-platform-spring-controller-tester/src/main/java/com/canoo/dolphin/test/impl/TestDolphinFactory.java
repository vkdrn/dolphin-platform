/*
 * Copyright 2015-2016 Canoo Engineering AG.
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
package com.canoo.dolphin.test.impl;

import com.canoo.dolphin.server.context.OpenDolphinFactory;
import com.canoo.dolphin.util.Assert;
import org.opendolphin.core.comm.DefaultInMemoryConfig;
import org.opendolphin.core.server.DefaultServerDolphin;

/**
 * Created by hendrikebbers on 05.02.16.
 */
public class TestDolphinFactory implements OpenDolphinFactory {

    private DefaultInMemoryConfig inMemoryConfig;

    public TestDolphinFactory(DefaultInMemoryConfig inMemoryConfig) {
        this.inMemoryConfig = Assert.requireNonNull(inMemoryConfig, "inMemoryConfig");
    }

    @Override
    public DefaultServerDolphin create() {
        return inMemoryConfig.getServerDolphin();
    }
}
