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

namespace Amazon.OpsWorks.Model
{
    /// <summary>
    /// Container for the parameters to the UpdateInstance operation.
    /// <para>Updates a specified instance.</para>
    /// </summary>
    /// <seealso cref="Amazon.OpsWorks.AmazonOpsWorks.UpdateInstance"/>
    public class UpdateInstanceRequest : AmazonWebServiceRequest
    {
        private string instanceId;
        private List<string> layerIds = new List<string>();
        private string instanceType;
        private string autoScalingType;
        private string hostname;
        private string os;
        private string sshKeyName;

        /// <summary>
        /// The instance ID.
        ///  
        /// </summary>
        public string InstanceId
        {
            get { return this.instanceId; }
            set { this.instanceId = value; }
        }

        /// <summary>
        /// Sets the InstanceId property
        /// </summary>
        /// <param name="instanceId">The value to set for the InstanceId property </param>
        /// <returns>this instance</returns>
        public UpdateInstanceRequest WithInstanceId(string instanceId)
        {
            this.instanceId = instanceId;
            return this;
        }
            

        // Check to see if InstanceId property is set
        internal bool IsSetInstanceId()
        {
            return this.instanceId != null;
        }

        /// <summary>
        /// The instance's layer IDs.
        ///  
        /// </summary>
        public List<string> LayerIds
        {
            get { return this.layerIds; }
            set { this.layerIds = value; }
        }
        /// <summary>
        /// Adds elements to the LayerIds collection
        /// </summary>
        /// <param name="layerIds">The values to add to the LayerIds collection </param>
        /// <returns>this instance</returns>
        public UpdateInstanceRequest WithLayerIds(params string[] layerIds)
        {
            foreach (string element in layerIds)
            {
                this.layerIds.Add(element);
            }

            return this;
        }

        /// <summary>
        /// Adds elements to the LayerIds collection
        /// </summary>
        /// <param name="layerIds">The values to add to the LayerIds collection </param>
        /// <returns>this instance</returns>
        public UpdateInstanceRequest WithLayerIds(IEnumerable<string> layerIds)
        {
            foreach (string element in layerIds)
            {
                this.layerIds.Add(element);
            }

            return this;
        }

        // Check to see if LayerIds property is set
        internal bool IsSetLayerIds()
        {
            return this.layerIds.Count > 0;
        }

        /// <summary>
        /// The instance type, which can be one of the following: <ul> <li>m1.small</li> <li>m1.medium</li> <li>m1.large</li> <li>m1.xlarge</li>
        /// <li>c1.medium</li> <li>c1.xlarge</li> <li>m2.xlarge</li> <li>m2.2xlarge</li> <li>m2.4xlarge</li> </ul>
        ///  
        /// </summary>
        public string InstanceType
        {
            get { return this.instanceType; }
            set { this.instanceType = value; }
        }

        /// <summary>
        /// Sets the InstanceType property
        /// </summary>
        /// <param name="instanceType">The value to set for the InstanceType property </param>
        /// <returns>this instance</returns>
        public UpdateInstanceRequest WithInstanceType(string instanceType)
        {
            this.instanceType = instanceType;
            return this;
        }
            

        // Check to see if InstanceType property is set
        internal bool IsSetInstanceType()
        {
            return this.instanceType != null;
        }

        /// <summary>
        /// The instance's auto scaling type, which has three possible values: <ul> <li><b>AlwaysRunning</b>: A 24x7 instance, which is not affected by
        /// auto scaling.</li> <li><b>TimeBasedAutoScaling</b>: A time-based auto scaling instance, which is started and stopped based on a specified
        /// schedule.</li> <li><b>LoadBasedAutoScaling</b>: A load-based auto scaling instance, which is started and stopped based on load metrics.</li>
        /// </ul>
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Allowed Values</term>
        ///         <description>AlwaysRunning, TimeBasedAutoScaling, LoadBasedAutoScaling</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string AutoScalingType
        {
            get { return this.autoScalingType; }
            set { this.autoScalingType = value; }
        }

        /// <summary>
        /// Sets the AutoScalingType property
        /// </summary>
        /// <param name="autoScalingType">The value to set for the AutoScalingType property </param>
        /// <returns>this instance</returns>
        public UpdateInstanceRequest WithAutoScalingType(string autoScalingType)
        {
            this.autoScalingType = autoScalingType;
            return this;
        }
            

        // Check to see if AutoScalingType property is set
        internal bool IsSetAutoScalingType()
        {
            return this.autoScalingType != null;
        }

        /// <summary>
        /// The instance host name.
        ///  
        /// </summary>
        public string Hostname
        {
            get { return this.hostname; }
            set { this.hostname = value; }
        }

        /// <summary>
        /// Sets the Hostname property
        /// </summary>
        /// <param name="hostname">The value to set for the Hostname property </param>
        /// <returns>this instance</returns>
        public UpdateInstanceRequest WithHostname(string hostname)
        {
            this.hostname = hostname;
            return this;
        }
            

        // Check to see if Hostname property is set
        internal bool IsSetHostname()
        {
            return this.hostname != null;
        }

        /// <summary>
        /// The instance operating system.
        ///  
        /// </summary>
        public string Os
        {
            get { return this.os; }
            set { this.os = value; }
        }

        /// <summary>
        /// Sets the Os property
        /// </summary>
        /// <param name="os">The value to set for the Os property </param>
        /// <returns>this instance</returns>
        public UpdateInstanceRequest WithOs(string os)
        {
            this.os = os;
            return this;
        }
            

        // Check to see if Os property is set
        internal bool IsSetOs()
        {
            return this.os != null;
        }

        /// <summary>
        /// The instance SSH key name.
        ///  
        /// </summary>
        public string SshKeyName
        {
            get { return this.sshKeyName; }
            set { this.sshKeyName = value; }
        }

        /// <summary>
        /// Sets the SshKeyName property
        /// </summary>
        /// <param name="sshKeyName">The value to set for the SshKeyName property </param>
        /// <returns>this instance</returns>
        public UpdateInstanceRequest WithSshKeyName(string sshKeyName)
        {
            this.sshKeyName = sshKeyName;
            return this;
        }
            

        // Check to see if SshKeyName property is set
        internal bool IsSetSshKeyName()
        {
            return this.sshKeyName != null;
        }
    }
}
    
