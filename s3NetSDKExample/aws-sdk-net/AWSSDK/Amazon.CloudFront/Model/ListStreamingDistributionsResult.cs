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

namespace Amazon.CloudFront.Model
{
    /// <summary>
    /// <para> The returned result of the corresponding request. </para>
    /// </summary>
    public class ListStreamingDistributionsResult  
    {
        
        private StreamingDistributionList streamingDistributionList;

        /// <summary>
        /// The StreamingDistributionList type.
        ///  
        /// </summary>
        public StreamingDistributionList StreamingDistributionList
        {
            get { return this.streamingDistributionList; }
            set { this.streamingDistributionList = value; }
        }

        /// <summary>
        /// Sets the StreamingDistributionList property
        /// </summary>
        /// <param name="streamingDistributionList">The value to set for the StreamingDistributionList property </param>
        /// <returns>this instance</returns>
        public ListStreamingDistributionsResult WithStreamingDistributionList(StreamingDistributionList streamingDistributionList)
        {
            this.streamingDistributionList = streamingDistributionList;
            return this;
        }
            

        // Check to see if StreamingDistributionList property is set
        internal bool IsSetStreamingDistributionList()
        {
            return this.streamingDistributionList != null;       
        }
    }
}
