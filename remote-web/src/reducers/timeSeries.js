const stats = (state = [], action) => {
    switch (action.type) {
        case 'STATS/SUCCESS':
            action.payload.date = Date.now();
            return [action.payload, ...state.slice(0, 60)];
        default:
            return state;
    }
};

export default stats