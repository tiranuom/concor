const schema = (state = {}, action) => {
    switch (action.type) {
        case 'SCHEMA_PENDING':
            return {
                ...state,
                status: 'pending'
            };
        case 'SCHEMA_ERROR':
            return {
                ...state,
                status: 'error',
                error: action.error
            };
        case 'SCHEMA_SUCCESS':
            return {
                ...state,
                status: 'success',
                data: action.data
            };
        default:
            return state;
    }
};