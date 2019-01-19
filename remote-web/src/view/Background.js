import React from 'react'
import Canves from "./serverView/Canves";

export default function () {

    let flows = [
        {
            id: "MO Flow",
            tasks: [
                {
                    id: "Start",
                    type: "start"
                },
                {
                    id: "Mobile Originated Translator",
                    type: "simple"
                },
                {
                    id: "Session Manager",
                    type: "single-threaded"
                },
                {
                    id: "MO Route",
                    type: "simple"
                },
                {
                    id: "End Point Resolver",
                    type: "synchronized-remote"
                },
                {
                    id: "AT Translator",
                    type: "simple"
                },
                {
                    id: "AT Message Sender",
                    type: "asynchronous-remote"
                },
                {
                    id: "Trans Logger",
                    type: "side-effect"
                },
                {
                    id: "Error Handler",
                    type: "catch"
                },
                {
                    id: "End",
                    type: "end"
                }
            ]
        }
    ];

    return <div>
        <header>Server 1</header>
        <Canves flows={flows}/>
    </div>
}