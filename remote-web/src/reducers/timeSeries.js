const stats = (state = [], action) => {
    switch (action.type) {
        case 'STATS/SUCCESS':
            let payload = action.payload;

            payload.date = Date.now();
            payload.latency = {};
            Object.keys(payload.tasks).forEach(key => {
                payload.latency[key] = payload.tasks[key].map(a=> a.latency).reduce((a, b) => a + b, 0)
            });

            let newState = [payload, ...state.slice(0, 60)];

            payload.averageTps = {};
            Object.keys(payload.tps).forEach(key => {
                payload.averageTps[key] = newState.filter((a, i) => i <  10).map(a => a.tps[key]).reduce((a, b) => a + b, 0) / Math.min(10, newState.length)
            });

            payload.averageLatency = {};
            Object.keys(payload.latency).forEach(key => {
                payload.averageLatency[key] = newState.filter((a, i) => i <  10).map(a => a.latency[key]).reduce((a, b) => a + b, 0) / Math.min(10, newState.length)
            });
            return newState;
        default:
            return state;
    }
};

export default stats