module.exports = {
    roots: ["<rootDir>/web"],
    transform: {
        "^.+\\.tsx?$": "ts-jest",
    },
    testRegex: "(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$",
    moduleFileExtensions: ["ts", "tsx", "js", "jsx", "json", "node"],
    moduleNameMapper: {
        "\\.(css|less|sass|scss)$": "<rootDir>/web/mocks/style-mock.js",
        "\\.(gif|ttf|eot|svg)$": "<rootDir>/web/mocks/file-mock.js"
    },
    globals: {
        "env": { 
            SERVER_API_URL: 'http://localhost:8080'
        }
    }    
};