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

namespace Amazon.RDS.Model
{
    /// <summary>
    /// Container for the parameters to the ListTagsForResource operation.
    /// <para> Lists all tags on a DB Instance.</para> <para>For an overview on tagging DB Instances, see DB Instance Tags. </para>
    /// </summary>
    /// <seealso cref="Amazon.RDS.AmazonRDS.ListTagsForResource"/>
    public class ListTagsForResourceRequest : AmazonWebServiceRequest
    {
        private string resourceName;

        /// <summary>
        /// The DB Instance with tags to be listed.
        ///  
        /// </summary>
        public string ResourceName
        {
            get { return this.resourceName; }
            set { this.resourceName = value; }
        }

        /// <summary>
        /// Sets the ResourceName property
        /// </summary>
        /// <param name="resourceName">The value to set for the ResourceName property </param>
        /// <returns>this instance</returns>
        public ListTagsForResourceRequest WithResourceName(string resourceName)
        {
            this.resourceName = resourceName;
            return this;
        }
            

        // Check to see if ResourceName property is set
        internal bool IsSetResourceName()
        {
            return this.resourceName != null;
        }
    }
}
    
