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
using System.Xml.Serialization;
using System.Text;
using System.IO;

using Amazon.Runtime;
using Amazon.Runtime.Internal;

namespace Amazon.ElasticTranscoder.Model
{
    /// <summary>
    /// Container for the parameters to the CreatePipeline operation.
    /// <para>To create a pipeline, send a POST request to the <c>2012-09-25/pipelines</c> resource.</para>
    /// </summary>
    /// <seealso cref="Amazon.ElasticTranscoder.AmazonElasticTranscoder.CreatePipeline"/>
    public class CreatePipelineRequest : AmazonWebServiceRequest
    {
        private string name;
        private string inputBucket;
        private string outputBucket;
        private string role;
        private Notifications notifications;

        /// <summary>
        /// The name of the pipeline. We recommend that the name be unique within the AWS account, but uniqueness is not enforced. Constraints: Maximum
        /// 40 characters.
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Length</term>
        ///         <description>1 - 40</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string Name
        {
            get { return this.name; }
            set { this.name = value; }
        }

        /// <summary>
        /// Sets the Name property
        /// </summary>
        /// <param name="name">The value to set for the Name property </param>
        /// <returns>this instance</returns>
        public CreatePipelineRequest WithName(string name)
        {
            this.name = name;
            return this;
        }
            

        // Check to see if Name property is set
        internal bool IsSetName()
        {
            return this.name != null;
        }

        /// <summary>
        /// The Amazon S3 bucket in which you saved the media files that you want to transcode.
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Length</term>
        ///         <description>1 - 255</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string InputBucket
        {
            get { return this.inputBucket; }
            set { this.inputBucket = value; }
        }

        /// <summary>
        /// Sets the InputBucket property
        /// </summary>
        /// <param name="inputBucket">The value to set for the InputBucket property </param>
        /// <returns>this instance</returns>
        public CreatePipelineRequest WithInputBucket(string inputBucket)
        {
            this.inputBucket = inputBucket;
            return this;
        }
            

        // Check to see if InputBucket property is set
        internal bool IsSetInputBucket()
        {
            return this.inputBucket != null;
        }

        /// <summary>
        /// The Amazon S3 bucket in which you want Elastic Transcoder to save the transcoded files.
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Length</term>
        ///         <description>1 - 255</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string OutputBucket
        {
            get { return this.outputBucket; }
            set { this.outputBucket = value; }
        }

        /// <summary>
        /// Sets the OutputBucket property
        /// </summary>
        /// <param name="outputBucket">The value to set for the OutputBucket property </param>
        /// <returns>this instance</returns>
        public CreatePipelineRequest WithOutputBucket(string outputBucket)
        {
            this.outputBucket = outputBucket;
            return this;
        }
            

        // Check to see if OutputBucket property is set
        internal bool IsSetOutputBucket()
        {
            return this.outputBucket != null;
        }

        /// <summary>
        /// The IAM Amazon Resource Name (ARN) for the role that you want Elastic Transcoder to use to create the pipeline.
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Pattern</term>
        ///         <description>^arn:aws:iam::\w{12}:role/.+$</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string Role
        {
            get { return this.role; }
            set { this.role = value; }
        }

        /// <summary>
        /// Sets the Role property
        /// </summary>
        /// <param name="role">The value to set for the Role property </param>
        /// <returns>this instance</returns>
        public CreatePipelineRequest WithRole(string role)
        {
            this.role = role;
            return this;
        }
            

        // Check to see if Role property is set
        internal bool IsSetRole()
        {
            return this.role != null;
        }

        /// <summary>
        /// The Amazon Simple Notification Service (Amazon SNS) topic that you want to notify to report job status. <important>To receive notifications,
        /// you must also subscribe to the new topic in the Amazon SNS console.</important> <ul> <li><b>Progressing</b>: The Amazon Simple Notification
        /// Service (Amazon SNS) topic that you want to notify when Elastic Transcoder has started to process the job.</li> <li><b>Completed</b>: The
        /// Amazon SNS topic that you want to notify when Elastic Transcoder has finished processing the job.</li> <li><b>Warning</b>: The Amazon SNS
        /// topic that you want to notify when Elastic Transcoder encounters a warning condition.</li> <li><b>Error</b>: The Amazon SNS topic that you
        /// want to notify when Elastic Transcoder encounters an error condition.</li> </ul>
        ///  
        /// </summary>
        public Notifications Notifications
        {
            get { return this.notifications; }
            set { this.notifications = value; }
        }

        /// <summary>
        /// Sets the Notifications property
        /// </summary>
        /// <param name="notifications">The value to set for the Notifications property </param>
        /// <returns>this instance</returns>
        public CreatePipelineRequest WithNotifications(Notifications notifications)
        {
            this.notifications = notifications;
            return this;
        }
            

        // Check to see if Notifications property is set
        internal bool IsSetNotifications()
        {
            return this.notifications != null;
        }
    }
}
    
