/*******************************************************************************
 *  Copyright 2008-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *  this file except in compliance with the License. A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file.
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations under the License.
 * *****************************************************************************
 *    __  _    _  ___
 *   (  )( \/\/ )/ __)
 *   /__\ \    / \__ \
 *  (_)(_) \/\/  (___/
 *
 *  AWS SDK for .NET
 *  API Version: 2006-03-01
 *
 */

using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

using Amazon.Auth.AccessControlPolicy;

namespace Amazon.S3.Model
{
    /// <summary>
    /// The parameters to set or update policy on a bucket.
    /// </summary>
    public class PutBucketPolicyRequest : S3Request
    {
        #region Private Members

        private string bucketName;
        private string policy;

        #endregion

        #region Properties

        /// <summary>
        /// The name of the bucket.
        /// </summary>
        [XmlElementAttribute(ElementName = "BucketName")]
        public string BucketName
        {
            get
            {
                return this.bucketName;
            }
            set
            {
                this.bucketName = value;
            }
        }

        /// <summary>
        /// Sets the name of the bucket.
        /// </summary>
        /// <param name="bucketName">The bucket name</param>
        /// <returns>this instance</returns>
        public PutBucketPolicyRequest WithBucketName(string bucketName)
        {
            this.BucketName = bucketName;
            return this;
        }

        /// <summary>
        /// Checks if BucketName property is set.
        /// </summary>
        /// <returns>true if BucketName property is set.</returns>
        internal bool IsSetBucketName()
        {
            return !System.String.IsNullOrEmpty(this.BucketName);
        }


        /// <summary>
        /// The policy to be set on the bucket.
        /// This is the JSON string representing the policy that will be applied to the S3 Bucket.
        /// </summary>
        public String Policy
        {
            get
            {
                return this.policy;
            }
            set
            {
                this.policy = value;
            }
        }

        /// <summary>
        /// Sets the policy to be set on the bucket.
        /// This is the JSON string representing the policy that will be applied to the S3 Bucket.
        /// </summary>
        /// <param name="policy">The JSON string for the policy</param>
        /// <returns>this instance</returns>
        public PutBucketPolicyRequest WithPolicy(string policy)
        {
            this.Policy = policy;
            return this;
        }

        /// <summary>
        /// Checks if policy property is set.
        /// </summary>
        /// <returns>true if Policy property is set.</returns>
        internal bool IsSetPolicy()
        {
            return !System.String.IsNullOrEmpty(this.Policy);
        }

        #endregion
    }
}
