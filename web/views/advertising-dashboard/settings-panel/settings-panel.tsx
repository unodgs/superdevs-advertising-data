import React, { useEffect, useState } from "react";
import Select from "react-select";
import makeAnimated from "react-select/animated";

import { useCampaignsQuery, useDataSourcesQuery } from "../../../hooks/advertising-hooks";
import { Campaign, DataSource } from "../../../models/advertising.model";

import "./settings-panel.scss";

export const SettingsPanel: React.FC<{onChange: (campaigns: number[]) => void}> = ({ onChange }) => {
    const [selectedDataSources, setSelectedDataSources] = useState<DataSource[]>([]);
    const [selectedCampaigns, setSelectedCampaigns] = useState<Campaign[]>([]);
    
    const { dataSources, isLoading: dataSourcesLoading } =
        useDataSourcesQuery();
    
    const { campaigns, isLoading: campaignsLoading } =
        useCampaignsQuery((selectedDataSources || []).map(ds => ds.id));
    
    useEffect(() => {
        const samplesCampaigns = selectedCampaigns?.length > 0 
            ? selectedCampaigns
            : (campaigns ? campaigns : [])
        
        onChange(samplesCampaigns.map(c => c.id));
    }, [campaigns, selectedCampaigns])
    
    const animatedComponents = makeAnimated();
    
    return <div className="settings-panel">
        <div className="settings-panel-data-sources">
            <label>Data sources</label>
            <Select
                placeholder="All"
                getOptionLabel={v => v.name}
                getOptionValue={v => v.id}
                isDisabled={dataSourcesLoading}
                isLoading={dataSourcesLoading}
                closeMenuOnSelect={true}
                components={animatedComponents}
                defaultValue={selectedDataSources}
                isMulti={true}
                options={dataSources}
                onChange={dataSources => {
                    setSelectedDataSources(dataSources as DataSource[]);
                }}
            />
        </div>
        <div className="settings-panel-campaigns">
            <label>Campaigns</label>
            <Select
                placeholder="All"
                getOptionLabel={v => v.name}
                getOptionValue={v => v.id}
                isDisabled={campaignsLoading}
                isLoading={campaignsLoading}
                closeMenuOnSelect={true}
                components={animatedComponents}
                defaultValue={selectedCampaigns}
                isMulti={true}
                options={campaigns}
                onChange={campaigns => {
                    setSelectedCampaigns(campaigns as Campaign[]);
                }}
            />
        </div>
    </div>;
}
