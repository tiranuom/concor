import RSAA from "redux-api-middleware/es/RSAA";

export const getSchema = () => ({
    [RSAA] : {
        endpoint: 'http://localhost:9090/rest/schema',
        method: 'GET',
        types: ["SCHEMA/PENDING", "SCHEMA/SUCCESS", "SCHEMA/FAILED"]
    }
});

export const getStats = () => ({
    [RSAA] : {
        endpoint: 'http://localhost:9090/rest/stats',
        method: 'GET',
        types: ["STATS/PENDING", "STATS/SUCCESS", "STATS/FAILED"]
    }
});

export const updateJoin = (data) => {
    if (data.joinType === 'NULL') {
        return {
            [RSAA] : {
                endpoint: 'http://localhost:9090/rest/join/remove',
                method: 'POST',
                body: JSON.stringify(data),
                headers: { 'Content-Type': 'application/json'},
                types: ["JOIN/PENDING", "JOIN/SUCCESS", "JOIN/FAILED"]
            }
        }
    } else return {
        [RSAA] : {
            endpoint: 'http://localhost:9090/rest/join/add',
            method: 'POST',
            body: JSON.stringify(data),
            headers: { 'Content-Type': 'application/json'},
            types: ["JOIN/PENDING", "JOIN/SUCCESS", "JOIN/FAILED"]
        }
    }
}

export const clearError = () => ({
    type: 'ERROR/CLEAR'
})