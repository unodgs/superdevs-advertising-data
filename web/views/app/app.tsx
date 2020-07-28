import React from "react";

import { ReactQueryConfigProvider, ReactQueryProviderConfig } from "react-query";
import { ServiceRepository } from "../../services/services"
import { ServicesContext } from "../../contexts/services-context/services.context";
import { AdvertisingDashboard } from "../advertising-dashboard/advertising-dashboard";

import "./app.scss";

export const App = (props: { services: ServiceRepository, queryConfig: ReactQueryProviderConfig }) => {
    return <ReactQueryConfigProvider config={props.queryConfig}>
        <ServicesContext.Provider value={props.services}>
            <div className="app">
                <header>
                    <h2>Advertising data visualiser</h2>
                </header>
                <AdvertisingDashboard/>
            </div>
        </ServicesContext.Provider>
    </ReactQueryConfigProvider>


}
