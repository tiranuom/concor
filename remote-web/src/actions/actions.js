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