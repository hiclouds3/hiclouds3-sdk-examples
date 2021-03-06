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
    /// Container for the parameters to the UpdateLayer operation.
    /// <para>Updates a specified layer.</para>
    /// </summary>
    /// <seealso cref="Amazon.OpsWorks.AmazonOpsWorks.UpdateLayer"/>
    public class UpdateLayerRequest : AmazonWebServiceRequest
    {
        private string layerId;
        private string name;
        private string shortname;
        private Dictionary<string,string> attributes = new Dictionary<string,string>();
        private string customInstanceProfileArn;
        private List<string> customSecurityGroupIds = new List<string>();
        private List<string> packages = new List<string>();
        private List<VolumeConfiguration> volumeConfigurations = new List<VolumeConfiguration>();
        private bool? enableAutoHealing;
        private bool? autoAssignElasticIps;
        private Recipes customRecipes;

        /// <summary>
        /// The layer ID.
        ///  
        /// </summary>
        public string LayerId
        {
            get { return this.layerId; }
            set { this.layerId = value; }
        }

        /// <summary>
        /// Sets the LayerId property
        /// </summary>
        /// <param name="layerId">The value to set for the LayerId property </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithLayerId(string layerId)
        {
            this.layerId = layerId;
            return this;
        }
            

        // Check to see if LayerId property is set
        internal bool IsSetLayerId()
        {
            return this.layerId != null;
        }

        /// <summary>
        /// The layer name, which is used by the console.
        ///  
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
        public UpdateLayerRequest WithName(string name)
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
        /// The layer short name, which is used internally by OpsWorks, by Chef. The shortname is also used as the name for the directory where your app
        /// files are installed. It can have a maximum of 200 characters and must be in the following format: /\A[a-z0-9\-\_\.]+\Z/.
        ///  
        /// </summary>
        public string Shortname
        {
            get { return this.shortname; }
            set { this.shortname = value; }
        }

        /// <summary>
        /// Sets the Shortname property
        /// </summary>
        /// <param name="shortname">The value to set for the Shortname property </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithShortname(string shortname)
        {
            this.shortname = shortname;
            return this;
        }
            

        // Check to see if Shortname property is set
        internal bool IsSetShortname()
        {
            return this.shortname != null;
        }

        /// <summary>
        /// One or more user-defined key/value pairs to be added to the stack attributes bag.
        ///  
        /// </summary>
        public Dictionary<string,string> Attributes
        {
            get { return this.attributes; }
            set { this.attributes = value; }
        }

        /// <summary>
        /// Adds the KeyValuePairs to the Attributes dictionary.
        /// </summary>
        /// <param name="pairs">The pairs to be added to the Attributes dictionary.</param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithAttributes(params KeyValuePair<string, string>[] pairs)
        {
            foreach (KeyValuePair<string, string> pair in pairs)
            {
                this.Attributes[pair.Key] = pair.Value;
            }

            return this;
        }

        // Check to see if Attributes property is set
        internal bool IsSetAttributes()
        {
            return this.attributes != null;
        }

        /// <summary>
        /// The ARN of an IAM profile to be used for all of the layer's EC2 instances. For more information about IAM ARNs, see <a
        /// href="http://docs.aws.amazon.com/IAM/latest/UserGuide/Using_Identifiers.html">Using Identifiers</a>.
        ///  
        /// </summary>
        public string CustomInstanceProfileArn
        {
            get { return this.customInstanceProfileArn; }
            set { this.customInstanceProfileArn = value; }
        }

        /// <summary>
        /// Sets the CustomInstanceProfileArn property
        /// </summary>
        /// <param name="customInstanceProfileArn">The value to set for the CustomInstanceProfileArn property </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithCustomInstanceProfileArn(string customInstanceProfileArn)
        {
            this.customInstanceProfileArn = customInstanceProfileArn;
            return this;
        }
            

        // Check to see if CustomInstanceProfileArn property is set
        internal bool IsSetCustomInstanceProfileArn()
        {
            return this.customInstanceProfileArn != null;
        }

        /// <summary>
        /// An array containing the layer's custom security group IDs.
        ///  
        /// </summary>
        public List<string> CustomSecurityGroupIds
        {
            get { return this.customSecurityGroupIds; }
            set { this.customSecurityGroupIds = value; }
        }
        /// <summary>
        /// Adds elements to the CustomSecurityGroupIds collection
        /// </summary>
        /// <param name="customSecurityGroupIds">The values to add to the CustomSecurityGroupIds collection </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithCustomSecurityGroupIds(params string[] customSecurityGroupIds)
        {
            foreach (string element in customSecurityGroupIds)
            {
                this.customSecurityGroupIds.Add(element);
            }

            return this;
        }

        /// <summary>
        /// Adds elements to the CustomSecurityGroupIds collection
        /// </summary>
        /// <param name="customSecurityGroupIds">The values to add to the CustomSecurityGroupIds collection </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithCustomSecurityGroupIds(IEnumerable<string> customSecurityGroupIds)
        {
            foreach (string element in customSecurityGroupIds)
            {
                this.customSecurityGroupIds.Add(element);
            }

            return this;
        }

        // Check to see if CustomSecurityGroupIds property is set
        internal bool IsSetCustomSecurityGroupIds()
        {
            return this.customSecurityGroupIds.Count > 0;
        }

        /// <summary>
        /// An array of <c>Package</c> objects that describe the layer's packages.
        ///  
        /// </summary>
        public List<string> Packages
        {
            get { return this.packages; }
            set { this.packages = value; }
        }
        /// <summary>
        /// Adds elements to the Packages collection
        /// </summary>
        /// <param name="packages">The values to add to the Packages collection </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithPackages(params string[] packages)
        {
            foreach (string element in packages)
            {
                this.packages.Add(element);
            }

            return this;
        }

        /// <summary>
        /// Adds elements to the Packages collection
        /// </summary>
        /// <param name="packages">The values to add to the Packages collection </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithPackages(IEnumerable<string> packages)
        {
            foreach (string element in packages)
            {
                this.packages.Add(element);
            }

            return this;
        }

        // Check to see if Packages property is set
        internal bool IsSetPackages()
        {
            return this.packages.Count > 0;
        }

        /// <summary>
        /// A <c>VolumeConfigurations</c> object that describes the layer's Amazon EBS volumes.
        ///  
        /// </summary>
        public List<VolumeConfiguration> VolumeConfigurations
        {
            get { return this.volumeConfigurations; }
            set { this.volumeConfigurations = value; }
        }
        /// <summary>
        /// Adds elements to the VolumeConfigurations collection
        /// </summary>
        /// <param name="volumeConfigurations">The values to add to the VolumeConfigurations collection </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithVolumeConfigurations(params VolumeConfiguration[] volumeConfigurations)
        {
            foreach (VolumeConfiguration element in volumeConfigurations)
            {
                this.volumeConfigurations.Add(element);
            }

            return this;
        }

        /// <summary>
        /// Adds elements to the VolumeConfigurations collection
        /// </summary>
        /// <param name="volumeConfigurations">The values to add to the VolumeConfigurations collection </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithVolumeConfigurations(IEnumerable<VolumeConfiguration> volumeConfigurations)
        {
            foreach (VolumeConfiguration element in volumeConfigurations)
            {
                this.volumeConfigurations.Add(element);
            }

            return this;
        }

        // Check to see if VolumeConfigurations property is set
        internal bool IsSetVolumeConfigurations()
        {
            return this.volumeConfigurations.Count > 0;
        }

        /// <summary>
        /// Whether to disable auto healing for the layer.
        ///  
        /// </summary>
        public bool EnableAutoHealing
        {
            get { return this.enableAutoHealing ?? default(bool); }
            set { this.enableAutoHealing = value; }
        }

        /// <summary>
        /// Sets the EnableAutoHealing property
        /// </summary>
        /// <param name="enableAutoHealing">The value to set for the EnableAutoHealing property </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithEnableAutoHealing(bool enableAutoHealing)
        {
            this.enableAutoHealing = enableAutoHealing;
            return this;
        }
            

        // Check to see if EnableAutoHealing property is set
        internal bool IsSetEnableAutoHealing()
        {
            return this.enableAutoHealing.HasValue;
        }

        /// <summary>
        /// Whether to automatically assign an <a href="http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/elastic-ip-addresses-eip.html">Elastic IP
        /// address</a> to the layer.
        ///  
        /// </summary>
        public bool AutoAssignElasticIps
        {
            get { return this.autoAssignElasticIps ?? default(bool); }
            set { this.autoAssignElasticIps = value; }
        }

        /// <summary>
        /// Sets the AutoAssignElasticIps property
        /// </summary>
        /// <param name="autoAssignElasticIps">The value to set for the AutoAssignElasticIps property </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithAutoAssignElasticIps(bool autoAssignElasticIps)
        {
            this.autoAssignElasticIps = autoAssignElasticIps;
            return this;
        }
            

        // Check to see if AutoAssignElasticIps property is set
        internal bool IsSetAutoAssignElasticIps()
        {
            return this.autoAssignElasticIps.HasValue;
        }

        /// <summary>
        /// A <c>LayerCustomRecipes</c> object that specifies the layer's custom recipes.
        ///  
        /// </summary>
        public Recipes CustomRecipes
        {
            get { return this.customRecipes; }
            set { this.customRecipes = value; }
        }

        /// <summary>
        /// Sets the CustomRecipes property
        /// </summary>
        /// <param name="customRecipes">The value to set for the CustomRecipes property </param>
        /// <returns>this instance</returns>
        public UpdateLayerRequest WithCustomRecipes(Recipes customRecipes)
        {
            this.customRecipes = customRecipes;
            return this;
        }
            

        // Check to see if CustomRecipes property is set
        internal bool IsSetCustomRecipes()
        {
            return this.customRecipes != null;
        }
    }
}
    
