import Axios, { AxiosInstance } from "axios"
import { Campaign, DataSample, DataSource } from "../../models/advertising.model";

export class AdvertisingApiService {
  #serversApi: AxiosInstance

  constructor() {
    this.#serversApi = Axios.create({
      baseURL: process.env.SERVER_API_URL + "/advertising"
    })
  }

  getDataSources(): Promise<DataSource[]> {
    return this.#serversApi
      .get<DataSource[]>("/data-sources")
      .then(res => res.data)
      .catch(() => [])
  }

  getCampaigns(dataSourceId: number): Promise<Campaign[]> {
    return this.#serversApi
      .get<Campaign[]>(`/campaigns/${dataSourceId}`)
      .then(res => res.data)
      .catch(() => [])
  }
  
  getDataSamples(campaignIds: number[]): Promise<DataSample[]> {
    return this.#serversApi
        .get<DataSample[]>(`/data-samples`)
        .then(res => res.data)
        .catch(() => [])
  }

}
