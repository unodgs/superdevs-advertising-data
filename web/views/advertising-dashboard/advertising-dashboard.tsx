import React, { useState } from "react";
import { SettingsPanel } from "./settings-panel/settings-panel";
import { ChartPanel } from "./chart-panel/chart-panel";

import "./advertising-dashboard.scss";

export const AdvertisingDashboard = () => {
    const [campaignIds, setCampaignIds] = useState<number[]>([]);
    
    return <div className="advertising-dashboard">
        <SettingsPanel onChange={setCampaignIds}/>
        <ChartPanel campaignIds={campaignIds}/>
    </div>
}