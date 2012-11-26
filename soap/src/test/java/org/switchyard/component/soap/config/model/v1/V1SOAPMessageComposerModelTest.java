/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved. 
 * See the copyright.txt in the distribution for a 
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use, 
 * modify, copy, or redistribute it subject to the terms and conditions 
 * of the GNU Lesser General Public License, v. 2.1. 
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General Public License, 
 * v.2.1 along with this distribution; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.switchyard.component.soap.config.model.v1;

import junit.framework.Assert;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.switchyard.component.soap.config.model.SOAPBindingModel;
import org.switchyard.config.model.ModelPuller;

/**
 * Test of atom binding model.
 */
public class V1SOAPMessageComposerModelTest {

    private static final String SOAP_BINDING = "soap-binding.xml";
    private static final String COMPOSER_FRAG = "message-composer.xml";
    
    @Test
    public void testReadConfigFragment() throws Exception {
        ModelPuller<V1SOAPMessageComposerModel> puller = new ModelPuller<V1SOAPMessageComposerModel>();
        V1SOAPMessageComposerModel model = puller.pull(COMPOSER_FRAG, getClass());
        Assert.assertTrue("Unwrap should be true", model.isUnwrapped());
    }
    
    @Test
    public void testReadConfigBinding() throws Exception {
        ModelPuller<SOAPBindingModel> puller = new ModelPuller<SOAPBindingModel>();
        SOAPBindingModel model = puller.pull(SOAP_BINDING, getClass());
        Assert.assertTrue(model.isModelValid());
        Assert.assertTrue("Unwrap should be true", model.getSOAPMessageComposer().isUnwrapped());
    }

    @Test
    public void testWriteConfig() throws Exception {
        V1SOAPMessageComposerModel scm = new V1SOAPMessageComposerModel();
        scm.setUnwrapped(true);

        V1SOAPMessageComposerModel refModel = new ModelPuller<V1SOAPMessageComposerModel>()
                .pull(COMPOSER_FRAG, getClass());
        
        XMLUnit.setIgnoreWhitespace(true);
        Diff diff = XMLUnit.compareXML(refModel.toString(), scm.toString());
        Assert.assertTrue(diff.toString(), diff.similar());
    }
}
