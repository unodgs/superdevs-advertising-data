import React from 'react';
import ReactDOM from 'react-dom';
import "@fortawesome/fontawesome-free/css/all.css";
import "./index.scss";
import { App } from "./views/app/app";
import { createServices } from "./services/services";

const services = createServices();

const queryConfig = {
    suspense: false,
    queries: {
        refetchOnWindowFocus: false
    }
}

ReactDOM.render(
    <React.StrictMode>
        <App
            services={services}
            queryConfig={queryConfig}
        />,
    </React.StrictMode>,
    document.getElementById('root')
);
