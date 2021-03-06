const schema = (state = {}, action) => {
    switch (action.type) {
        case 'SCHEMA/PENDING':
            return {
                ...state,
                status: 'pending'
            };
        case 'SCHEMA/SUCCESS':
            return {
                ...state,
                status: 'success',
                data: action.payload
            };
        case 'SCHEMA/FAILED':
            return {
                ...state,
                status: 'error',
                error: action.payload
            };
        default:
            return state;
    }
};

export default schema