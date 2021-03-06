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

namespace Amazon.DataPipeline.Model
{
    /// <summary>
    /// <para>Contains a logical operation for comparing the value of a field with a specified value.</para>
    /// </summary>
    public class Operator
    {
        
        private string type;
        private List<string> values = new List<string>();

        /// <summary>
        /// The logical operation to be performed: equal (EQ), equal reference (REF_EQ), less than or equal (LE), greater than or equal (GE), or between
        /// (BETWEEN). Equal reference (REF_EQ) can be used only with reference fields. The other comparison types can be used only with String fields.
        /// The comparison types you can use apply only to certain object fields, as detailed below. The comparison operators EQ and REF_EQ act on the
        /// following fields: <ul> <li>name</li> <li>@sphere</li> <li>parent</li> <li>@componentParent</li> <li>@instanceParent</li> <li>@status</li>
        /// <li>@scheduledStartTime</li> <li>@scheduledEndTime</li> <li>@actualStartTime</li> <li>@actualEndTime</li> </ul> The comparison operators GE,
        /// LE, and BETWEEN act on the following fields: <ul> <li>@scheduledStartTime</li> <li>@scheduledEndTime</li> <li>@actualStartTime</li>
        /// <li>@actualEndTime</li> </ul> Note that fields beginning with the at sign (@) are read-only and set by the web service. When you name
        /// fields, you should choose names containing only alpha-numeric values, as symbols may be reserved by AWS Data Pipeline. A best practice for
        /// user-defined fields that you add to a pipeline is to prefix their name with the string "my".
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Allowed Values</term>
        ///         <description>EQ, REF_EQ, LE, GE, BETWEEN</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string Type
        {
            get { return this.type; }
            set { this.type = value; }
        }

        /// <summary>
        /// Sets the Type property
        /// </summary>
        /// <param name="type">The value to set for the Type property </param>
        /// <returns>this instance</returns>
        public Operator WithType(string type)
        {
            this.type = type;
            return this;
        }
            

        // Check to see if Type property is set
        internal bool IsSetType()
        {
            return this.type != null;
        }

        /// <summary>
        /// The value that the actual field value will be compared with.
        ///  
        /// </summary>
        public List<string> Values
        {
            get { return this.values; }
            set { this.values = value; }
        }
        /// <summary>
        /// Adds elements to the Values collection
        /// </summary>
        /// <param name="values">The values to add to the Values collection </param>
        /// <returns>this instance</returns>
        public Operator WithValues(params string[] values)
        {
            foreach (string element in values)
            {
                this.values.Add(element);
            }

            return this;
        }

        /// <summary>
        /// Adds elements to the Values collection
        /// </summary>
        /// <param name="values">The values to add to the Values collection </param>
        /// <returns>this instance</returns>
        public Operator WithValues(IEnumerable<string> values)
        {
            foreach (string element in values)
            {
                this.values.Add(element);
            }

            return this;
        }

        // Check to see if Values property is set
        internal bool IsSetValues()
        {
            return this.values.Count > 0;
        }
    }
}
