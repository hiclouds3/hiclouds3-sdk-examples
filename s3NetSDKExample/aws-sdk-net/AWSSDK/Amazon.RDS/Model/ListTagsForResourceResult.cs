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
    /// 
    /// </summary>
    public class ListTagsForResourceResult
    {
        
        private List<Tag> tagList = new List<Tag>();

        /// <summary>
        /// List of tags returned by the ListTagsForResource operation.
        ///  
        /// </summary>
        public List<Tag> TagList
        {
            get { return this.tagList; }
            set { this.tagList = value; }
        }
        /// <summary>
        /// Adds elements to the TagList collection
        /// </summary>
        /// <param name="tagList">The values to add to the TagList collection </param>
        /// <returns>this instance</returns>
        public ListTagsForResourceResult WithTagList(params Tag[] tagList)
        {
            foreach (Tag element in tagList)
            {
                this.tagList.Add(element);
            }

            return this;
        }

        /// <summary>
        /// Adds elements to the TagList collection
        /// </summary>
        /// <param name="tagList">The values to add to the TagList collection </param>
        /// <returns>this instance</returns>
        public ListTagsForResourceResult WithTagList(IEnumerable<Tag> tagList)
        {
            foreach (Tag element in tagList)
            {
                this.tagList.Add(element);
            }

            return this;
        }

        // Check to see if TagList property is set
        internal bool IsSetTagList()
        {
            return this.tagList.Count > 0;
        }
    }
}
