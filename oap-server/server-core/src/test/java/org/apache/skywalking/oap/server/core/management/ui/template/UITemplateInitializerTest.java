/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.core.management.ui.template;

import java.util.List;
import org.apache.skywalking.oap.server.library.util.BooleanUtils;
import org.junit.Assert;
import org.junit.Test;

public class UITemplateInitializerTest {
    @Test
    public void testReadFile() {
        UITemplateInitializer initializer = new UITemplateInitializer(this.getClass()
                .getClassLoader()
                .getResourceAsStream(
                        "ui-initialized-templates.yml"));
        final List<UITemplate> uiTemplates = initializer.read();

        Assert.assertEquals(2, uiTemplates.size());
        UITemplate uiTemplate = uiTemplates.get(0);
        Assert.assertEquals("APM (Agent based)", uiTemplate.getName());
        Assert.assertTrue(uiTemplate.getConfiguration().length() > 0);
        Assert.assertEquals(BooleanUtils.TRUE, uiTemplate.getActivated());
        Assert.assertEquals(BooleanUtils.FALSE, uiTemplate.getDisabled());

        uiTemplate = uiTemplates.get(1);
        Assert.assertEquals("APM (Service Mesh)", uiTemplate.getName());
        Assert.assertTrue(uiTemplate.getConfiguration().length() > 0);
        Assert.assertEquals(BooleanUtils.FALSE, uiTemplate.getActivated());
        Assert.assertEquals(BooleanUtils.TRUE, uiTemplate.getDisabled());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTemplateConflict() {
        UITemplateInitializer initializer = new UITemplateInitializer(this.getClass()
                .getClassLoader()
                .getResourceAsStream(
                        "ui-initialized-templates-conflict.yml"));
        final List<UITemplate> uiTemplates = initializer.read();
    }
}
