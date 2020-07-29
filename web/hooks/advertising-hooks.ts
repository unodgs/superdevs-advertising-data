import React from "react";
import { ServicesContext } from "../contexts/services-context/services.context";
import { useQuery } from "react-query"

export function useDataSourcesQuery() {
    const { advertisingApiService } = React.useContext(ServicesContext)!
    
    const { data, isLoading } = useQuery(
        `data-sources`,
        async () => {
            return await advertisingApiService.getDataSources();
        }
    )

    return { dataSources: data, isLoading }
}

export function useCampaignsQuery(dataSourceIds: number[]) {
    const { advertisingApiService } = React.useContext(ServicesContext)!
    
    const { data, isLoading } = useQuery(
        `campaigns-${dataSourceIds.join(':')}`,
        async () => {
            return await advertisingApiService.getCampaigns(dataSourceIds)
        }
    )

    return { campaigns: data, isLoading }
}

export function useDateSamplesQuery(campaignIds: number[]) {
    const { advertisingApiService } = React.useContext(ServicesContext)!
    
    const { data, isLoading } = useQuery(
        `date-samples-${campaignIds.join(':')}`,
        async () => {
            return await advertisingApiService.getDateSamples(campaignIds)
        }
    )

    return { dateSamples: data, isLoading }
}
