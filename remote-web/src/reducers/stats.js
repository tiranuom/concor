const stats = (state = {}, action) => {
    switch (action.type) {
        case 'STATS/PENDING':
            return {
                ...state,
                status: 'pending'
            };
        case 'STATS/SUCCESS':
            return {
                ...state,
                status: 'success',
                data: action.payload
            };
        case 'STATS/FAILED':
            return {
                ...state,
                status: 'error',
                error: action.payload
            };
        default:
            return state;
    }
};

export default stats