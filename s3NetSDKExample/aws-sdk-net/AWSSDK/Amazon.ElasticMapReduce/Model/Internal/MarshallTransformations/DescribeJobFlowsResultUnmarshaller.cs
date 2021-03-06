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
using System.Collections.Generic;

using Amazon.ElasticMapReduce.Model;
using Amazon.Runtime.Internal.Transform;

namespace Amazon.ElasticMapReduce.Model.Internal.MarshallTransformations
{
     /// <summary>
     ///   DescribeJobFlowsResult Unmarshaller
     /// </summary>
    internal class DescribeJobFlowsResultUnmarshaller : IUnmarshaller<DescribeJobFlowsResult, XmlUnmarshallerContext>, IUnmarshaller<DescribeJobFlowsResult, JsonUnmarshallerContext> 
    {
        public DescribeJobFlowsResult Unmarshall(XmlUnmarshallerContext context) 
        {
            DescribeJobFlowsResult describeJobFlowsResult = new DescribeJobFlowsResult();
            int originalDepth = context.CurrentDepth;
            int targetDepth = originalDepth + 1;
            
            if (context.IsStartOfDocument) 
               targetDepth += 2;
            
            while (context.Read())
            {
                if (context.IsStartElement || context.IsAttribute)
                { 
                    if (context.TestExpression("JobFlows/member", targetDepth))
                    {
                        describeJobFlowsResult.JobFlows.Add(JobFlowDetailUnmarshaller.GetInstance().Unmarshall(context));
                            
                        continue;
                    }
                }
                else if (context.IsEndElement && context.CurrentDepth < originalDepth)
                {
                    return describeJobFlowsResult;
                }
            }
                        


            return describeJobFlowsResult;
        }

        public DescribeJobFlowsResult Unmarshall(JsonUnmarshallerContext context) 
        {
            return null;
        }

        private static DescribeJobFlowsResultUnmarshaller instance;

        public static DescribeJobFlowsResultUnmarshaller GetInstance() 
        {
            if (instance == null) 
               instance = new DescribeJobFlowsResultUnmarshaller();

            return instance;
        }
    }
}
    
