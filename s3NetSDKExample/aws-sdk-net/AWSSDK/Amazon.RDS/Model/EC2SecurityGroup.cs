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

namespace Amazon.RDS.Model
{
    /// <summary>
    /// <para> This data type is used as a response element in the following actions: </para>
    /// <ul>
    /// <li> AuthorizeDBSecurityGroupIngress </li>
    /// <li> DescribeDBSecurityGroups </li>
    /// <li> RevokeDBSecurityGroupIngress </li>
    /// 
    /// </ul>
    /// </summary>
    public class EC2SecurityGroup
    {
        
        private string status;
        private string eC2SecurityGroupName;
        private string eC2SecurityGroupId;
        private string eC2SecurityGroupOwnerId;

        /// <summary>
        /// Provides the status of the EC2 security group. Status can be "authorizing", "authorized", "revoking", and "revoked".
        ///  
        /// </summary>
        public string Status
        {
            get { return this.status; }
            set { this.status = value; }
        }

        /// <summary>
        /// Sets the Status property
        /// </summary>
        /// <param name="status">The value to set for the Status property </param>
        /// <returns>this instance</returns>
        public EC2SecurityGroup WithStatus(string status)
        {
            this.status = status;
            return this;
        }
            

        // Check to see if Status property is set
        internal bool IsSetStatus()
        {
            return this.status != null;
        }

        /// <summary>
        /// Specifies the name of the EC2 Security Group.
        ///  
        /// </summary>
        public string EC2SecurityGroupName
        {
            get { return this.eC2SecurityGroupName; }
            set { this.eC2SecurityGroupName = value; }
        }

        /// <summary>
        /// Sets the EC2SecurityGroupName property
        /// </summary>
        /// <param name="eC2SecurityGroupName">The value to set for the EC2SecurityGroupName property </param>
        /// <returns>this instance</returns>
        public EC2SecurityGroup WithEC2SecurityGroupName(string eC2SecurityGroupName)
        {
            this.eC2SecurityGroupName = eC2SecurityGroupName;
            return this;
        }
            

        // Check to see if EC2SecurityGroupName property is set
        internal bool IsSetEC2SecurityGroupName()
        {
            return this.eC2SecurityGroupName != null;
        }

        /// <summary>
        /// Specifies the id of the EC2 Security Group.
        ///  
        /// </summary>
        public string EC2SecurityGroupId
        {
            get { return this.eC2SecurityGroupId; }
            set { this.eC2SecurityGroupId = value; }
        }

        /// <summary>
        /// Sets the EC2SecurityGroupId property
        /// </summary>
        /// <param name="eC2SecurityGroupId">The value to set for the EC2SecurityGroupId property </param>
        /// <returns>this instance</returns>
        public EC2SecurityGroup WithEC2SecurityGroupId(string eC2SecurityGroupId)
        {
            this.eC2SecurityGroupId = eC2SecurityGroupId;
            return this;
        }
            

        // Check to see if EC2SecurityGroupId property is set
        internal bool IsSetEC2SecurityGroupId()
        {
            return this.eC2SecurityGroupId != null;
        }

        /// <summary>
        /// Specifies the AWS ID of the owner of the EC2 security group specified in the <c>EC2SecurityGroupName</c> field.
        ///  
        /// </summary>
        public string EC2SecurityGroupOwnerId
        {
            get { return this.eC2SecurityGroupOwnerId; }
            set { this.eC2SecurityGroupOwnerId = value; }
        }

        /// <summary>
        /// Sets the EC2SecurityGroupOwnerId property
        /// </summary>
        /// <param name="eC2SecurityGroupOwnerId">The value to set for the EC2SecurityGroupOwnerId property </param>
        /// <returns>this instance</returns>
        public EC2SecurityGroup WithEC2SecurityGroupOwnerId(string eC2SecurityGroupOwnerId)
        {
            this.eC2SecurityGroupOwnerId = eC2SecurityGroupOwnerId;
            return this;
        }
            

        // Check to see if EC2SecurityGroupOwnerId property is set
        internal bool IsSetEC2SecurityGroupOwnerId()
        {
            return this.eC2SecurityGroupOwnerId != null;
        }
    }
}
