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
    /// Container for the parameters to the DeletePreset operation.
    /// <para>To delete a preset, send a DELETE request to the <c>/2012-09-25/presets/[presetId] </c> resource.</para> <para><b>NOTE:</b> If the
    /// preset has been used, you cannot delete it. </para>
    /// </summary>
    /// <seealso cref="Amazon.ElasticTranscoder.AmazonElasticTranscoder.DeletePreset"/>
    public class DeletePresetRequest : AmazonWebServiceRequest
    {
        private string id;

        /// <summary>
        /// The identifier of the preset for which you want to get detailed information.
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Pattern</term>
        ///         <description>^\d{13}-\w{6}$</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string Id
        {
            get { return this.id; }
            set { this.id = value; }
        }

        /// <summary>
        /// Sets the Id property
        /// </summary>
        /// <param name="id">The value to set for the Id property </param>
        /// <returns>this instance</returns>
        public DeletePresetRequest WithId(string id)
        {
            this.id = id;
            return this;
        }
            

        // Check to see if Id property is set
        internal bool IsSetId()
        {
            return this.id != null;
        }
    }
}
    
