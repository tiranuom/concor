import React from 'react'

export default function ({x, y}) {
    return <g transform={`translate(${x}, ${y})`} >
        <ellipse cx="40" cy="40" rx="10" ry="10" fill="#000000" stroke="#000000" pointerEvents="none"/>
    </g>
}