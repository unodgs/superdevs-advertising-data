import React from "react";
import { SettingsPanel } from "./settings-panel/settings-panel";
import { ChartPanel } from "./chart-panel/chart-panel";

import "./advertising-dashboard.scss";

export const AdvertisingDashboard = () => {
    return <div className="advertising-dashboard">
        <SettingsPanel/>
        <ChartPanel/>
    </div>
}