/*******************************************************************************
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
 *  API Version: 2012-11-05
 */

using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Text;


namespace Amazon.SQS.Model
{
    /// <summary>
    /// A single SQS message along with an <c>Id</c>.
    /// </summary>
    public class SendMessageBatchRequestEntry
    {
        private string idField;
        private string messageBodyField;
        private int? delaySecondsField;

        /// <summary>
        /// An identifier for the message in this batch.
        /// This is used to communicate the result.
        /// </summary>
        /// <remarks>
        /// The <c>Id</c>s of a batch request need to be unique within the request.
        /// </remarks>
        public string Id
        {
            get { return this.idField; }
            set { this.idField = value; }
        }

        /// <summary>
        /// Sets an identifier for the message in this batch.
        /// </summary>
        /// <param name="id">An identifier for the message in this batch.</param>
        /// <returns>this instance</returns>
        public SendMessageBatchRequestEntry WithId(string id)
        {
            this.idField = id;
            return this;
        }

        /// <summary>
        /// Checks if Id property is set
        /// </summary>
        /// <returns>true if Id property is set</returns>
        public bool IsSetId()
        {
            return this.idField != null;
        }

        /// <summary>
        /// The message to send.
        /// </summary>
        /// <remarks>
        /// The maximum allowed message size is 8 KB.
        /// The following list shows the characters (in Unicode) allowed in your message, according to the W3C XML specification
        /// (for more information, go to http://www.w3.org/TR/REC-xml/#charsets). If you send any characters not included in the
        /// list, your request will be rejected.
        ///
        /// #x9 | #xA | #xD | [#x20 to #xD7FF] | [#xE000 to #xFFFD] | [#x10000 to #x10FFFF]
        /// </remarks>
        [XmlElementAttribute(ElementName = "MessageBody")]
        public string MessageBody
        {
            get { return this.messageBodyField; }
            set { this.messageBodyField = value; }
        }

        /// <summary>
        /// Sets the message to send.
        /// </summary>
        /// <remarks>
        /// The maximum allowed message size is 8 KB.
        /// The following list shows the characters (in Unicode) allowed in your message, according to the W3C XML specification
        /// (for more information, go to http://www.w3.org/TR/REC-xml/#charsets). If you send any characters not included in the
        /// list, your request will be rejected.
        ///
        /// #x9 | #xA | #xD | [#x20 to #xD7FF] | [#xE000 to #xFFFD] | [#x10000 to #x10FFFF]
        /// </remarks>
        /// <param name="messageBody">The message to send.</param>
        /// <returns>this instance</returns>
        public SendMessageBatchRequestEntry WithMessageBody(string messageBody)
        {
            this.messageBodyField = messageBody;
            return this;
        }

        /// <summary>
        /// Checks if MessageBody property is set
        /// </summary>
        /// <returns>true if MessageBody property is set</returns>
        public bool IsSetMessageBody()
        {
            return this.messageBodyField != null;
        }

        /// <summary>
        /// The number of seconds to delay the message from being available for processing.
        /// </summary>
        public int DelaySeconds
        {
            get { return this.delaySecondsField.GetValueOrDefault(); }
            set { this.delaySecondsField = value; }
        }

        /// <summary>
        /// Sets the number of seconds to delay the message from being available for processing.
        /// </summary>
        /// <param name="delaySeconds">The number of seconds to delay the message from being available for processing.</param>
        /// <returns>this instance</returns>
        public SendMessageBatchRequestEntry WithDelaySeconds(int delaySeconds)
        {
            this.delaySecondsField = delaySeconds;
            return this;
        }

        /// <summary>
        /// Checks if DelaySeconds property is set
        /// </summary>
        /// <returns>true if DelaySeconds property is set</returns>
        public bool IsSetDelaySeconds()
        {
            return this.delaySecondsField.HasValue;
        }
    }
}
