/*
 * Copyright 2010-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 * 
 *  http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.services.elasticbeanstalk.model.transform;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.events.XMLEvent;

import com.amazonaws.services.elasticbeanstalk.model.*;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.transform.MapEntry;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers.*;

/**
 * Environment Info Description StAX Unmarshaller
 */
public class EnvironmentInfoDescriptionStaxUnmarshaller implements Unmarshaller<EnvironmentInfoDescription, StaxUnmarshallerContext> {

    public EnvironmentInfoDescription unmarshall(StaxUnmarshallerContext context) throws Exception {
        EnvironmentInfoDescription environmentInfoDescription = new EnvironmentInfoDescription();
        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;

        if (context.isStartOfDocument()) targetDepth += 2;

        while (true) {
            XMLEvent xmlEvent = context.nextEvent();
            if (xmlEvent.isEndDocument()) return environmentInfoDescription;

            if (xmlEvent.isAttribute() || xmlEvent.isStartElement()) {
                if (context.testExpression("InfoType", targetDepth)) {
                    environmentInfoDescription.setInfoType(StringStaxUnmarshaller.getInstance().unmarshall(context));
                    continue;
                }
                if (context.testExpression("Ec2InstanceId", targetDepth)) {
                    environmentInfoDescription.setEc2InstanceId(StringStaxUnmarshaller.getInstance().unmarshall(context));
                    continue;
                }
                if (context.testExpression("SampleTimestamp", targetDepth)) {
                    environmentInfoDescription.setSampleTimestamp(DateStaxUnmarshaller.getInstance().unmarshall(context));
                    continue;
                }
                if (context.testExpression("Message", targetDepth)) {
                    environmentInfoDescription.setMessage(StringStaxUnmarshaller.getInstance().unmarshall(context));
                    continue;
                }
            } else if (xmlEvent.isEndElement()) {
                if (context.getCurrentDepth() < originalDepth) {
                    return environmentInfoDescription;
                }
            }
        }
    }

    private static EnvironmentInfoDescriptionStaxUnmarshaller instance;
    public static EnvironmentInfoDescriptionStaxUnmarshaller getInstance() {
        if (instance == null) instance = new EnvironmentInfoDescriptionStaxUnmarshaller();
        return instance;
    }
}
    