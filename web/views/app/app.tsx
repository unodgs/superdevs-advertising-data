import React from "react";

import { ReactQueryConfigProvider, ReactQueryProviderConfig, useIsFetching } from "react-query";
import { ServiceRepository } from "../../services/services"
import { ServicesContext } from "../../contexts/services-context/services.context";
import { AdvertisingDashboard } from "../advertising-dashboard/advertising-dashboard";

import { AppHeader } from "./app-header/app-header";
import { AppContent } from "./app-content/app-content";

import { GlobalLoader } from "../../components/global-loader/global-loader";

import "./app.scss";

export const App = (props: { services: ServiceRepository, queryConfig: ReactQueryProviderConfig }) => {
    return <ReactQueryConfigProvider config={props.queryConfig}>
        <ServicesContext.Provider value={props.services}>
            <div className="app">
                <AppHeader/>
                <GlobalLoader enabled={useIsFetching() > 0}/>
                <AppContent>
                    <AdvertisingDashboard/>
                </AppContent>
            </div>
        </ServicesContext.Provider>
    </ReactQueryConfigProvider>
}
