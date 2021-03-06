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
    /// Container for the parameters to the CloneStack operation.
    /// <para>Creates a clone of a specified stack.</para>
    /// </summary>
    /// <seealso cref="Amazon.OpsWorks.AmazonOpsWorks.CloneStack"/>
    public class CloneStackRequest : AmazonWebServiceRequest
    {
        private string sourceStackId;
        private string name;
        private string region;
        private Dictionary<string,string> attributes = new Dictionary<string,string>();
        private string serviceRoleArn;
        private string defaultInstanceProfileArn;
        private string defaultOs;
        private string hostnameTheme;
        private string defaultAvailabilityZone;
        private string customJson;
        private bool? useCustomCookbooks;
        private Source customCookbooksSource;
        private string defaultSshKeyName;
        private bool? clonePermissions;
        private List<string> cloneAppIds = new List<string>();

        /// <summary>
        /// The source stack ID.
        ///  
        /// </summary>
        public string SourceStackId
        {
            get { return this.sourceStackId; }
            set { this.sourceStackId = value; }
        }

        /// <summary>
        /// Sets the SourceStackId property
        /// </summary>
        /// <param name="sourceStackId">The value to set for the SourceStackId property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithSourceStackId(string sourceStackId)
        {
            this.sourceStackId = sourceStackId;
            return this;
        }
            

        // Check to see if SourceStackId property is set
        internal bool IsSetSourceStackId()
        {
            return this.sourceStackId != null;
        }

        /// <summary>
        /// The cloned stack name.
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
        public CloneStackRequest WithName(string name)
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
        /// The cloned stack AWS region, such as "us-east-1". For more information about AWS regions, see <a
        /// href="http://docs.aws.amazon.com/general/latest/gr/rande.html">Regions and Endpoints</a>
        ///  
        /// </summary>
        public string Region
        {
            get { return this.region; }
            set { this.region = value; }
        }

        /// <summary>
        /// Sets the Region property
        /// </summary>
        /// <param name="region">The value to set for the Region property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithRegion(string region)
        {
            this.region = region;
            return this;
        }
            

        // Check to see if Region property is set
        internal bool IsSetRegion()
        {
            return this.region != null;
        }

        /// <summary>
        /// A list of stack attributes and values as key/value pairs to be added to the cloned stack.
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
        public CloneStackRequest WithAttributes(params KeyValuePair<string, string>[] pairs)
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
        /// The stack AWS Identity and Access Management (IAM) role, which allows OpsWorks to work with AWS resources on your behalf. You must set this
        /// parameter to the Amazon Resource Name (ARN) for an existing IAM role. If you create a stack by using the OpsWorks console, it creates the
        /// role for you. You can obtain an existing stack's IAM ARN programmatically by calling <a>DescribePermissions</a>. For more information about
        /// IAM ARNs, see <a href="http://docs.aws.amazon.com/IAM/latest/UserGuide/Using_Identifiers.html">Using Identifiers</a>.
        ///  
        /// </summary>
        public string ServiceRoleArn
        {
            get { return this.serviceRoleArn; }
            set { this.serviceRoleArn = value; }
        }

        /// <summary>
        /// Sets the ServiceRoleArn property
        /// </summary>
        /// <param name="serviceRoleArn">The value to set for the ServiceRoleArn property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithServiceRoleArn(string serviceRoleArn)
        {
            this.serviceRoleArn = serviceRoleArn;
            return this;
        }
            

        // Check to see if ServiceRoleArn property is set
        internal bool IsSetServiceRoleArn()
        {
            return this.serviceRoleArn != null;
        }

        /// <summary>
        /// The ARN of an IAM profile that is the default profile for all of the stack's EC2 instances. For more information about IAM ARNs, see <a
        /// href="http://docs.aws.amazon.com/IAM/latest/UserGuide/Using_Identifiers.html">Using Identifiers</a>.
        ///  
        /// </summary>
        public string DefaultInstanceProfileArn
        {
            get { return this.defaultInstanceProfileArn; }
            set { this.defaultInstanceProfileArn = value; }
        }

        /// <summary>
        /// Sets the DefaultInstanceProfileArn property
        /// </summary>
        /// <param name="defaultInstanceProfileArn">The value to set for the DefaultInstanceProfileArn property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithDefaultInstanceProfileArn(string defaultInstanceProfileArn)
        {
            this.defaultInstanceProfileArn = defaultInstanceProfileArn;
            return this;
        }
            

        // Check to see if DefaultInstanceProfileArn property is set
        internal bool IsSetDefaultInstanceProfileArn()
        {
            return this.defaultInstanceProfileArn != null;
        }

        /// <summary>
        /// The cloned stack default operating system, which must be either "Amazon Linux" or "Ubuntu 12.04 LTS".
        ///  
        /// </summary>
        public string DefaultOs
        {
            get { return this.defaultOs; }
            set { this.defaultOs = value; }
        }

        /// <summary>
        /// Sets the DefaultOs property
        /// </summary>
        /// <param name="defaultOs">The value to set for the DefaultOs property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithDefaultOs(string defaultOs)
        {
            this.defaultOs = defaultOs;
            return this;
        }
            

        // Check to see if DefaultOs property is set
        internal bool IsSetDefaultOs()
        {
            return this.defaultOs != null;
        }

        /// <summary>
        /// The stack's host name theme, with spaces are replaced by underscores. The theme is used to generate hostnames for the stack's instances. By
        /// default, <c>HostnameTheme</c> is set to Layer_Dependent, which creates hostnames by appending integers to the layer's shortname. The other
        /// themes are: <ul> <li>Baked_Goods</li> <li>Clouds</li> <li>European_Cities</li> <li>Fruits</li> <li>Greek_Deities</li>
        /// <li>Legendary_Creatures_from_Japan</li> <li>Planets_and_Moons</li> <li>Roman_Deities</li> <li>Scottish_Islands</li> <li>US_Cities</li>
        /// <li>Wild_Cats</li> </ul> To obtain a generated hostname, call <c>GetHostNameSuggestion</c>, which returns a hostname based on the current
        /// theme.
        ///  
        /// </summary>
        public string HostnameTheme
        {
            get { return this.hostnameTheme; }
            set { this.hostnameTheme = value; }
        }

        /// <summary>
        /// Sets the HostnameTheme property
        /// </summary>
        /// <param name="hostnameTheme">The value to set for the HostnameTheme property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithHostnameTheme(string hostnameTheme)
        {
            this.hostnameTheme = hostnameTheme;
            return this;
        }
            

        // Check to see if HostnameTheme property is set
        internal bool IsSetHostnameTheme()
        {
            return this.hostnameTheme != null;
        }

        /// <summary>
        /// The cloned stack's Availability Zone. For more information, see <a href="http://docs.aws.amazon.com/general/latest/gr/rande.html">Regions
        /// and Endpoints</a>.
        ///  
        /// </summary>
        public string DefaultAvailabilityZone
        {
            get { return this.defaultAvailabilityZone; }
            set { this.defaultAvailabilityZone = value; }
        }

        /// <summary>
        /// Sets the DefaultAvailabilityZone property
        /// </summary>
        /// <param name="defaultAvailabilityZone">The value to set for the DefaultAvailabilityZone property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithDefaultAvailabilityZone(string defaultAvailabilityZone)
        {
            this.defaultAvailabilityZone = defaultAvailabilityZone;
            return this;
        }
            

        // Check to see if DefaultAvailabilityZone property is set
        internal bool IsSetDefaultAvailabilityZone()
        {
            return this.defaultAvailabilityZone != null;
        }

        /// <summary>
        /// A string that contains user-defined, custom JSON. It is used to override the corresponding default stack configuration JSON values. The
        /// string should be in the following format and must escape characters such as '"'.: <c>"{\"key1\": \"value1\", \"key2\": \"value2\",...}"</c>
        ///  
        /// </summary>
        public string CustomJson
        {
            get { return this.customJson; }
            set { this.customJson = value; }
        }

        /// <summary>
        /// Sets the CustomJson property
        /// </summary>
        /// <param name="customJson">The value to set for the CustomJson property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithCustomJson(string customJson)
        {
            this.customJson = customJson;
            return this;
        }
            

        // Check to see if CustomJson property is set
        internal bool IsSetCustomJson()
        {
            return this.customJson != null;
        }

        /// <summary>
        /// Whether to use custom cookbooks.
        ///  
        /// </summary>
        public bool UseCustomCookbooks
        {
            get { return this.useCustomCookbooks ?? default(bool); }
            set { this.useCustomCookbooks = value; }
        }

        /// <summary>
        /// Sets the UseCustomCookbooks property
        /// </summary>
        /// <param name="useCustomCookbooks">The value to set for the UseCustomCookbooks property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithUseCustomCookbooks(bool useCustomCookbooks)
        {
            this.useCustomCookbooks = useCustomCookbooks;
            return this;
        }
            

        // Check to see if UseCustomCookbooks property is set
        internal bool IsSetUseCustomCookbooks()
        {
            return this.useCustomCookbooks.HasValue;
        }

        /// <summary>
        /// Contains the information required to retrieve an app or cookbook from a repository.
        ///  
        /// </summary>
        public Source CustomCookbooksSource
        {
            get { return this.customCookbooksSource; }
            set { this.customCookbooksSource = value; }
        }

        /// <summary>
        /// Sets the CustomCookbooksSource property
        /// </summary>
        /// <param name="customCookbooksSource">The value to set for the CustomCookbooksSource property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithCustomCookbooksSource(Source customCookbooksSource)
        {
            this.customCookbooksSource = customCookbooksSource;
            return this;
        }
            

        // Check to see if CustomCookbooksSource property is set
        internal bool IsSetCustomCookbooksSource()
        {
            return this.customCookbooksSource != null;
        }

        /// <summary>
        /// A default SSH key for the stack instances. You can override this value when you create or update an instance.
        ///  
        /// </summary>
        public string DefaultSshKeyName
        {
            get { return this.defaultSshKeyName; }
            set { this.defaultSshKeyName = value; }
        }

        /// <summary>
        /// Sets the DefaultSshKeyName property
        /// </summary>
        /// <param name="defaultSshKeyName">The value to set for the DefaultSshKeyName property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithDefaultSshKeyName(string defaultSshKeyName)
        {
            this.defaultSshKeyName = defaultSshKeyName;
            return this;
        }
            

        // Check to see if DefaultSshKeyName property is set
        internal bool IsSetDefaultSshKeyName()
        {
            return this.defaultSshKeyName != null;
        }

        /// <summary>
        /// Whether to clone the source stack's permissions.
        ///  
        /// </summary>
        public bool ClonePermissions
        {
            get { return this.clonePermissions ?? default(bool); }
            set { this.clonePermissions = value; }
        }

        /// <summary>
        /// Sets the ClonePermissions property
        /// </summary>
        /// <param name="clonePermissions">The value to set for the ClonePermissions property </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithClonePermissions(bool clonePermissions)
        {
            this.clonePermissions = clonePermissions;
            return this;
        }
            

        // Check to see if ClonePermissions property is set
        internal bool IsSetClonePermissions()
        {
            return this.clonePermissions.HasValue;
        }

        /// <summary>
        /// A list of source stack app IDs to be included in the cloned stack.
        ///  
        /// </summary>
        public List<string> CloneAppIds
        {
            get { return this.cloneAppIds; }
            set { this.cloneAppIds = value; }
        }
        /// <summary>
        /// Adds elements to the CloneAppIds collection
        /// </summary>
        /// <param name="cloneAppIds">The values to add to the CloneAppIds collection </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithCloneAppIds(params string[] cloneAppIds)
        {
            foreach (string element in cloneAppIds)
            {
                this.cloneAppIds.Add(element);
            }

            return this;
        }

        /// <summary>
        /// Adds elements to the CloneAppIds collection
        /// </summary>
        /// <param name="cloneAppIds">The values to add to the CloneAppIds collection </param>
        /// <returns>this instance</returns>
        public CloneStackRequest WithCloneAppIds(IEnumerable<string> cloneAppIds)
        {
            foreach (string element in cloneAppIds)
            {
                this.cloneAppIds.Add(element);
            }

            return this;
        }

        // Check to see if CloneAppIds property is set
        internal bool IsSetCloneAppIds()
        {
            return this.cloneAppIds.Count > 0;
        }
    }
}
    
