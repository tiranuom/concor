import React from 'react'
import Queue from "./Queue";

export default function({x, y, latency = 0, itemId, queue, onClick}) {

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
            <text x="120" y="20" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica" alignmentBaseline={"central"}>{latency}
            </text>
        </g>
        <rect x="73" y="30" width="155" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
        <g transform="translate(30,-30)">
            <path d="M 186.92 45.34 L 191.32 49.74 L 178.35 49.65 L 178.26 36.68 L 182.66 41.08 L 219.08 4.66 L 214.68 0.26 L 227.65 0.35 L 227.74 13.32 L 223.34 8.92 Z"
                  fill="none" stroke="#000000" strokeLinejoin="round" strokeMiterlimit="10" pointerEvents="none"/>
        </g>
        {queue &&
        <Queue x={15} y={70} color={'yellow'} queueSize={queue.queueSize}/>
        }
        <g transform="translate(0,30)">
            <foreignObject>
                <div onClick={onClick && onClick.bind(this)} style={{width: 80, height: 110, opacity: 0.1}}></div>
            </foreignObject>
        </g>
    </g>

}