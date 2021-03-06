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
    using System;
    using System.Collections.Generic;
    using System.IO;
    using Amazon.ElasticTranscoder.Model;
    using Amazon.Runtime.Internal.Transform;

    namespace Amazon.ElasticTranscoder.Model.Internal.MarshallTransformations
    {
      /// <summary>
      /// JobOutputUnmarshaller
      /// </summary>
      internal class JobOutputUnmarshaller : IUnmarshaller<JobOutput, XmlUnmarshallerContext>, IUnmarshaller<JobOutput, JsonUnmarshallerContext>
      {
        JobOutput IUnmarshaller<JobOutput, XmlUnmarshallerContext>.Unmarshall(XmlUnmarshallerContext context)
        {
          throw new NotImplementedException();
        }

        public JobOutput Unmarshall(JsonUnmarshallerContext context)
        {
            JobOutput jobOutput = new JobOutput();
          
            int originalDepth = context.CurrentDepth;
            int targetDepth = originalDepth + 1;
            while (context.Read())
            {
                if ((context.IsKey) && (context.CurrentDepth == targetDepth))
                {
                context.Read();
                context.Read();
              
              if (context.TestExpression("Key", targetDepth))
              {
                jobOutput.Key = StringUnmarshaller.GetInstance().Unmarshall(context);
                continue;
              }
  
              if (context.TestExpression("ThumbnailPattern", targetDepth))
              {
                jobOutput.ThumbnailPattern = StringUnmarshaller.GetInstance().Unmarshall(context);
                continue;
              }
  
              if (context.TestExpression("Rotate", targetDepth))
              {
                jobOutput.Rotate = StringUnmarshaller.GetInstance().Unmarshall(context);
                continue;
              }
  
              if (context.TestExpression("PresetId", targetDepth))
              {
                jobOutput.PresetId = StringUnmarshaller.GetInstance().Unmarshall(context);
                continue;
              }
  
              if (context.TestExpression("Status", targetDepth))
              {
                jobOutput.Status = StringUnmarshaller.GetInstance().Unmarshall(context);
                continue;
              }
  
              if (context.TestExpression("StatusDetail", targetDepth))
              {
                jobOutput.StatusDetail = StringUnmarshaller.GetInstance().Unmarshall(context);
                continue;
              }
  
                }
                else if (context.IsEndElement && context.CurrentDepth <= originalDepth)
                {
                    return jobOutput;
                }
            }
          

            return jobOutput;
        }

        private static JobOutputUnmarshaller instance;
        public static JobOutputUnmarshaller GetInstance()
        {
            if (instance == null)
                instance = new JobOutputUnmarshaller();
            return instance;
        }
    }
}
  
