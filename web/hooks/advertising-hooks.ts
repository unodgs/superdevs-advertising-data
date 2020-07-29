import React from "react";
import { ServicesContext } from "../contexts/services-context/services.context";
import { queryCache, useMutation, useQuery } from "react-query"

export function useDataSourcesQuery() {
    const { advertisingApiService } = React.useContext(ServicesContext)!
    
    const { data, isLoading } = useQuery(
        `data-sources`,
        async () => {
            const dataSources = await advertisingApiService.getDataSources()
            return dataSources;
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

// export function useRebootedServerStatusRefreshQuery(server) {
//   const { serverApiService, serverStatusService } = React.useContext(
//     ServicesContext
//   )
//
//   return useQuery(
//     `server-${server.id}`,
//     async () => {
//       const rebootedServer = await serverApiService.findServer(server.id)
//       updateServer(rebootedServer.id, { ...rebootedServer, busy: false })
//       return rebootedServer
//     },
//     {
//       refetchInterval: 1000,
//       enabled: serverStatusService.isServerRebooting(server)
//     }
//   )
// }
//
// export function useServerActions() {
//   const { serverApiService } = React.useContext(ServicesContext)
//
//   const mutationWatcher = {
//     onMutate: (serverId) => {
//       updateServer(serverId, { busy: true })
//     },
//     onSuccess: (server) => {
//       updateServer(server.id, server)
//     },
//     onSettled: (server) => {
//       updateServer(server.id, { busy: false })
//     }
//   }
//
//   const [turnServerOn] = useMutation(
//     (serverId) => serverApiService.turnServerOn(serverId),
//     mutationWatcher
//   )
//
//   const [turnServerOff] = useMutation(
//     (serverId) => serverApiService.turnServerOff(serverId),
//     mutationWatcher
//   )
//
//   const [rebootServer] = useMutation(
//     (serverId) => serverApiService.rebootServer(serverId),
//     mutationWatcher
//   )
//
//   return { turnServerOn, turnServerOff, rebootServer }
// }
//
// export function updateServer(serverId, server) {
//   queryCache.setQueryData(SERVERS_QUERY_KEY, (servers) => {
//     return servers.map((s) => (s.id === serverId ? { ...s, ...server } : s))
//   })
// }
