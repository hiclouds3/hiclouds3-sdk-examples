﻿/*******************************************************************************
 * Copyright 2008-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 * this file except in compliance with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 * *****************************************************************************
 *    __  _    _  ___
 *   (  )( \/\/ )/ __)
 *   /__\ \    / \__ \
 *  (_)(_) \/\/  (___/
 *
 *  AWS SDK for .NET
 */

using System;
using System.Collections.Generic;
using System.IO;
using System.Xml.Serialization;
using System.Text;

namespace Amazon.EC2.Model
{
    /// <summary>
    /// Name of an attribute account
    /// </summary>
    public class AccountAttributeName
    {
        private string attributeNameField;

        /// <summary>
        /// Name of the attribute.
        /// </summary>
        [XmlElementAttribute(ElementName = "AttributeName")]
        public string AttributeName
        {
            get { return this.attributeNameField; }
            set { this.attributeNameField = value; }
        }

        /// <summary>
        /// Sets the name of the attribute.
        /// </summary>
        /// <param name="attributeName">Name of the attribute.</param>
        /// <returns>this instance</returns>
        public AccountAttributeName WithAttributeName(string attributeName)
        {
            this.attributeNameField = attributeName;
            return this;
        }

        /// <summary>
        /// Checks if AttributeName property is set
        /// </summary>
        /// <returns>true if AttributeName property is set</returns>
        public bool IsSetAttributeName()
        {
            return this.attributeNameField != null;
        }
    }
}
