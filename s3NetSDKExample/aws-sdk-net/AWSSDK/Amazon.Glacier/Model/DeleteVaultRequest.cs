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

namespace Amazon.Glacier.Model
{
    /// <summary>
    /// Container for the parameters to the DeleteVault operation.
    /// <para>This operation deletes a vault. Amazon Glacier will delete a vault only if there are no archives in the vault as of the last inventory
    /// and there have been no writes to the vault since the last inventory. If either of these conditions is not satisfied, the vault deletion
    /// fails (that is, the vault is not removed) and Amazon Glacier returns an error. You can use DescribeVault to return the number of archives in
    /// a vault, and you can use Initiate a Job (POST jobs) to initiate a new inventory retrieval for a vault. The inventory contains the archive
    /// IDs you use to delete archives using Delete Archive (DELETE archive).</para> <para>This operation is idempotent.</para> <para>An AWS account
    /// has full permission to perform all operations (actions). However, AWS Identity and Access Management (IAM) users don't have any permissions
    /// by default. You must grant them explicit permission to perform specific actions. For more information, see Access Control Using AWS Identity
    /// and Access Management (IAM).</para> <para> For conceptual information and underlying REST API, go to Deleting a Vault in Amazon Glacier and
    /// Delete Vault in the <i>Amazon Glacier Developer Guide</i> .
    /// </para>
    /// </summary>
    /// <seealso cref="Amazon.Glacier.AmazonGlacier.DeleteVault"/>
    public class DeleteVaultRequest : AmazonWebServiceRequest
    {
        private string accountId;
        private string vaultName;

        /// <summary>
        /// The <c>AccountId</c> is the AWS Account ID. You can specify either the AWS Account ID or optionally a '-', in which case Amazon Glacier uses
        /// the AWS Account ID associated with the credentials used to sign the request. If you specify your Account ID, do not include hyphens in it.
        ///  
        /// </summary>
        public string AccountId
        {
            get { return this.accountId; }
            set { this.accountId = value; }
        }

        /// <summary>
        /// Sets the AccountId property
        /// </summary>
        /// <param name="accountId">The value to set for the AccountId property </param>
        /// <returns>this instance</returns>
        public DeleteVaultRequest WithAccountId(string accountId)
        {
            this.accountId = accountId;
            return this;
        }
            

        // Check to see if AccountId property is set
        internal bool IsSetAccountId()
        {
            return this.accountId != null;       
        }

        /// <summary>
        /// The name of the vault.
        ///  
        /// </summary>
        public string VaultName
        {
            get { return this.vaultName; }
            set { this.vaultName = value; }
        }

        /// <summary>
        /// Sets the VaultName property
        /// </summary>
        /// <param name="vaultName">The value to set for the VaultName property </param>
        /// <returns>this instance</returns>
        public DeleteVaultRequest WithVaultName(string vaultName)
        {
            this.vaultName = vaultName;
            return this;
        }
            

        // Check to see if VaultName property is set
        internal bool IsSetVaultName()
        {
            return this.vaultName != null;       
        }
    }
}
    
