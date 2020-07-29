import React from "react";
import { useDateSamplesQuery } from "../../../hooks/advertising-hooks";
import { Line } from "react-chartjs-2";

import "./chart-panel.scss";

export const ChartPanel: React.FC<{ campaignIds: number[] }> = ({ campaignIds }) => {
    const { dateSamples, isLoading } = useDateSamplesQuery(campaignIds);
    
    const clicksData = React.useMemo(
        () => {
            const samples = dateSamples || [];
            const labels = samples.map(ds => ds.sampleDate);
            
            return {
                labels: labels,
                datasets: [{
                    label: 'Clicks',
                    data: samples.map(ds => ds.clicks),
                    fill: false,
                    backgroundColor: '#D7ECFB',
                    borderColor: '#36A2EB'
                }],
            }
        },
        [dateSamples]
    );

    const impressionsData = React.useMemo(
        () => {
            const samples = dateSamples || [];
            const labels = samples.map(ds => ds.sampleDate);
            
            return {
                labels: labels,
                datasets: [{
                    label: 'Impressions',
                    data: samples.map(ds => ds.impressions),
                    fill: false,
                    backgroundColor: '#FFE0E6',
                    borderColor: '#FF6383'
                }],
            }
        },
        [dateSamples]
    );

    const options = React.useMemo(
        () => {
            return {
                responsive: true,
                hoverMode: 'index',
                stacked: false,
                maintainAspectRatio: true
            }
        },
        [dateSamples]
    );
    
    return <div className="chart-panel" data-testid="chart-panel">
        <Line data={clicksData} options={options}/>
        <Line data={impressionsData} options={options}/>
    </div>;
}
