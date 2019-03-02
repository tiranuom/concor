import React from 'react'

import Flow from "./drawables/Flow";

export const toTime = function (str) {
    try {
        var value = parseInt(str);
        if (value / 1000 < 1) return value + "ns";
        if (value / 1000000 < 1) return value / 1000 + "ms";
        return Math.round(value/ 1000) / 1000 + "s";
    } catch (e) {
        return str;
    }
};

export default function ({flows}) {

    return <div style={{padding: 5}}>
        <svg style={{width: '100%', height: 1000, borderColor: 'red', borderStyle: 'solid', borderWidth: 1}}>
            <defs>
                <marker id="arrow" markerWidth="10" markerHeight="10" refX="0" refY="3" orient="auto" markerUnits="strokeWidth" viewBox="0 0 20 20">
                    <path d="M0,0 L0,6 L9,3 z" />
                </marker>
            </defs>
            {flows.map((flow, index) => <Flow flow={flow} yOffset={(index + 1) * 100} key={flow.id} />)}
        </svg>
    </div>
}