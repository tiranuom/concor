export default (state = "", action) => {
    switch (action.type) {
        case 'ERROR/CLEAR':
            return "";
        case 'SCHEMA/FAILED':
            return action.payload.toString();
        case 'STATS/FAILED':
            console.log("STATS/FAILED", action.payload)
            return action.payload.toString();
        case "JOIN/FAILED":
            return action.payload.toString();
        case "JOIN/SUCCESS":
            if (!action.payload.success) return action.payload.error;
        default:
            return state;
    }
}