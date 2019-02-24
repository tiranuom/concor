import React from 'react'
import Queue from "./Queue";
import {Button, OverlayTrigger, Popover} from "react-bootstrap";
import JoinSelection from "./popover/JoinSelection";
import {toTime} from "../Canves";

export default function({x, y, queue, task, itemId, onClick, flowId}) {

    const popover = <Popover id="popover-positioned-left" title="Popover left" >

    </Popover>

    return <g transform={`translate(${x}, ${y}) scale(0.5)`} >
        <rect x="4" y="30" width="224" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
        <g transform="translate(97.5,-20)">
            <text x="18" y="12" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica">{itemId}
            </text>
        </g>

        <g transform="translate(0,220)">
            <path d="M 0 0 L 228 0" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <path d="M 4 -4 L 04 4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <path d="M 224 4 L 224 -4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <text x="120" y="20" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica" alignmentBaseline={"central"}>{toTime(task.latency)}
            </text>
        </g>
        <rect x="73" y="30" width="155" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>

        {!!task.queue &&
        <Queue x={15} y={70} queue={task.queue}/>
        }
        <g transform="translate(0,30)">
            <JoinSelection id={task.id} flowId={flowId}/>
        </g>
    </g>

}