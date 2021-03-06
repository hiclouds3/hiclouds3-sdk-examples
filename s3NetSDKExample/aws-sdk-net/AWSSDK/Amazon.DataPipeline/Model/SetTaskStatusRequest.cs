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

namespace Amazon.DataPipeline.Model
{
    /// <summary>
    /// Container for the parameters to the SetTaskStatus operation.
    /// <para> Notifies AWS Data Pipeline that a task is completed and provides information about the final status. The task runner calls this
    /// action regardless of whether the task was sucessful. The task runner does not need to call SetTaskStatus for tasks that are canceled by the
    /// web service during a call to ReportTaskProgress. </para>
    /// </summary>
    /// <seealso cref="Amazon.DataPipeline.AmazonDataPipeline.SetTaskStatus"/>
    public class SetTaskStatusRequest : AmazonWebServiceRequest
    {
        private string taskId;
        private string taskStatus;
        private int? errorCode;
        private string errorMessage;
        private string errorStackTrace;

        /// <summary>
        /// Identifies the task assigned to the task runner. This value is set in the <a>TaskObject</a> that is returned by the <a>PollForTask</a>
        /// action.
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Length</term>
        ///         <description>1 - 2048</description>
        ///     </item>
        ///     <item>
        ///         <term>Pattern</term>
        ///         <description>[\u0020-\uD7FF\uE000-\uFFFD\uD800\uDC00-\uDBFF\uDFFF\r\n\t]*</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string TaskId
        {
            get { return this.taskId; }
            set { this.taskId = value; }
        }

        /// <summary>
        /// Sets the TaskId property
        /// </summary>
        /// <param name="taskId">The value to set for the TaskId property </param>
        /// <returns>this instance</returns>
        public SetTaskStatusRequest WithTaskId(string taskId)
        {
            this.taskId = taskId;
            return this;
        }
            

        // Check to see if TaskId property is set
        internal bool IsSetTaskId()
        {
            return this.taskId != null;
        }

        /// <summary>
        /// If FINISHED, the task successfully completed. If FAILED the task ended unsuccessfully. The FALSE value is used by preconditions.
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Allowed Values</term>
        ///         <description>FINISHED, FAILED, FALSE</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string TaskStatus
        {
            get { return this.taskStatus; }
            set { this.taskStatus = value; }
        }

        /// <summary>
        /// Sets the TaskStatus property
        /// </summary>
        /// <param name="taskStatus">The value to set for the TaskStatus property </param>
        /// <returns>this instance</returns>
        public SetTaskStatusRequest WithTaskStatus(string taskStatus)
        {
            this.taskStatus = taskStatus;
            return this;
        }
            

        // Check to see if TaskStatus property is set
        internal bool IsSetTaskStatus()
        {
            return this.taskStatus != null;
        }

        /// <summary>
        /// If an error occurred during the task, specifies a numerical value that represents the error. This value is set on the physical attempt
        /// object. It is used to display error information to the user. The web service does not parse this value.
        ///  
        /// </summary>
        public int ErrorCode
        {
            get { return this.errorCode ?? default(int); }
            set { this.errorCode = value; }
        }

        /// <summary>
        /// Sets the ErrorCode property
        /// </summary>
        /// <param name="errorCode">The value to set for the ErrorCode property </param>
        /// <returns>this instance</returns>
        public SetTaskStatusRequest WithErrorCode(int errorCode)
        {
            this.errorCode = errorCode;
            return this;
        }
            

        // Check to see if ErrorCode property is set
        internal bool IsSetErrorCode()
        {
            return this.errorCode.HasValue;
        }

        /// <summary>
        /// If an error occurred during the task, specifies a text description of the error. This value is set on the physical attempt object. It is
        /// used to display error information to the user. The web service does not parse this value.
        ///  
        /// </summary>
        public string ErrorMessage
        {
            get { return this.errorMessage; }
            set { this.errorMessage = value; }
        }

        /// <summary>
        /// Sets the ErrorMessage property
        /// </summary>
        /// <param name="errorMessage">The value to set for the ErrorMessage property </param>
        /// <returns>this instance</returns>
        public SetTaskStatusRequest WithErrorMessage(string errorMessage)
        {
            this.errorMessage = errorMessage;
            return this;
        }
            

        // Check to see if ErrorMessage property is set
        internal bool IsSetErrorMessage()
        {
            return this.errorMessage != null;
        }

        /// <summary>
        /// If an error occurred during the task, specifies the stack trace associated with the error. This value is set on the physical attempt object.
        /// It is used to display error information to the user. The web service does not parse this value.
        ///  
        /// <para>
        /// <b>Constraints:</b>
        /// <list type="definition">
        ///     <item>
        ///         <term>Length</term>
        ///         <description>0 - 1024</description>
        ///     </item>
        ///     <item>
        ///         <term>Pattern</term>
        ///         <description>[\u0020-\uD7FF\uE000-\uFFFD\uD800\uDC00-\uDBFF\uDFFF\r\n\t]*</description>
        ///     </item>
        /// </list>
        /// </para>
        /// </summary>
        public string ErrorStackTrace
        {
            get { return this.errorStackTrace; }
            set { this.errorStackTrace = value; }
        }

        /// <summary>
        /// Sets the ErrorStackTrace property
        /// </summary>
        /// <param name="errorStackTrace">The value to set for the ErrorStackTrace property </param>
        /// <returns>this instance</returns>
        public SetTaskStatusRequest WithErrorStackTrace(string errorStackTrace)
        {
            this.errorStackTrace = errorStackTrace;
            return this;
        }
            

        // Check to see if ErrorStackTrace property is set
        internal bool IsSetErrorStackTrace()
        {
            return this.errorStackTrace != null;
        }
    }
}
    
