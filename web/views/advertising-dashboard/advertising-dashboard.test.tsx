import React from "react"
import { render } from "@testing-library/react"
import { useCampaignsQuery, useDataSourcesQuery, useDateSamplesQuery } from "../../hooks/advertising-hooks";
import { ServicesContext } from '../../contexts/services-context/services.context';
import { createServices } from "../../services/services";
import { AdvertisingDashboard } from "./advertising-dashboard";

jest.mock("../../hooks/advertising-hooks")
jest.mock('react-chartjs-2', () => ({
    Line: () => null,
}))

describe("Advertising dashboard", () => {
    it("should render the dashboard", async () => {
        const dateSamples = [
            { "sampleDate": "06.03.2019", "clicks": 3215, "impressions": 60249 },
            { "sampleDate": "07.03.2019", "clicks": 2496, "impressions": 88334 },
            { "sampleDate": "08.03.2019", "clicks": 669, "impressions": 27175 }
        ];

        const dataSources = [
            { "id": 1, "name": "Facebook Ads" },
            { "id": 2, "name": "Google Adwords" },
            { "id": 3, "name": "Google Analytics" },
            { "id": 4, "name": "Mailchimp" }
        ];

        const campaigns = [
            { "id": 825, "name": "2nd Edition EuroSciCon Heart Diseases- Paris - Conference" },
            { "id": 703, "name": "30th European Heart Diseases - Amsterdam - Conference" },
            { "id": 981, "name": "60 Second 04032019" },
            { "id": 527, "name": "60 Second 08022019" },
            { "id": 1067, "name": "60 Second 09042019" },
            { "id": 1015, "name": "60 Second 13032019" },
            { "id": 1049, "name": "60 Second 26032019" },
            { "id": 1316, "name": "60 Second 26042019" },
            { "id": 132, "name": "60 Second 29122018" },
            { "id": 283, "name": "AF Symposium - Boston - Conference" },
            { "id": 359, "name": "Ace of Hearts Installs" },
            { "id": 374, "name": "Ace of Hearts Installs - iOs" },
            { "id": 194, "name": "American Society of Echocardio - Hawaii - Conference" },
            { "id": 339, "name": "Arab Health - Dubai - Conference" },
            { "id": 512, "name": "BSC Congress - Belgium - Conference" },
            { "id": 1006, "name": "Brian Anthony - Webinar" },
            { "id": 601, "name": "Brutkasten - Opendata Event" },
            { "id": 636, "name": "Content Push" },
            { "id": 1238, "name": "Course Launch Campaign" },
        ];

        const services = createServices();

        (useDateSamplesQuery as any).mockReturnValue({
            isLoading: false,
            dateSamples
        });

        (useDataSourcesQuery as any).mockReturnValue({
            isLoading: false,
            dataSources
        });

        (useCampaignsQuery as any).mockReturnValue({
            isLoading: false,
            campaigns
        });


        const { findAllByText, findByTestId } = render(
            <ServicesContext.Provider value={services}>
                <AdvertisingDashboard/>
            </ServicesContext.Provider>
        )

        await findAllByText("All")
        await findByTestId("chart-panel")
    })
})
