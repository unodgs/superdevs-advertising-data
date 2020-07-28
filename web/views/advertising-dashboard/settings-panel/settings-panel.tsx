import React, { useState } from "react";
import Select from "react-select";
import makeAnimated from "react-select/animated";

import "./settings-panel.scss";
import { useCampaignsQuery, useDataSourcesQuery } from "../../../hooks/advertising-hooks";

type OptionRecord = { value: number, label: string }

export const SettingsPanel = () => {
    const [selectedDataSources, setSelectedDataSources] = useState([] as OptionRecord[]);
    const [selectedCampaigns, setSelectedCampaigns] = useState([] as OptionRecord[]);
    
    const { dataSources, isLoading: dataSourcesLoading } = useDataSourcesQuery();
    const { campaigns, isLoading: campaignsLoading } = useCampaignsQuery(selectedDataSources.map(ds => ds.value));
    
    const dataSourcesOptions = dataSourcesLoading
        ? []
        : dataSources!.map(ds => ({
            value: ds.id,
            label: ds.name
        }));
    
    const campaignsOptions = campaignsLoading
        ? []
        : campaigns!.map(c => ({
            value: c.id,
            label: c.name
        }));
    
    const animatedComponents = makeAnimated();
    
    return <div className="settings-panel">
        <label>Data source</label>
        <Select
            closeMenuOnSelect={false}
            components={animatedComponents}
            defaultValue={selectedDataSources}
            isMulti={true}
            options={dataSourcesOptions}
            onChange={dataSources => setSelectedDataSources(dataSources as OptionRecord[])}
        />
        <label>Campaigns</label>
        <Select
            closeMenuOnSelect={false}
            components={animatedComponents}
            defaultValue={[]}
            isMulti={true}
            options={campaignsOptions}
            onChange={campaigns => setSelectedDataSources(campaigns as OptionRecord[])}
        />
    </div>;
}
