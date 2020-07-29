import React from "react"
import { render } from "@testing-library/react"
import { GlobalLoader } from "./global-loader";

describe("Global loader", () => {
    it("should render loader only if enabled prop is true", () => {
        const { getByTestId } = render(<GlobalLoader enabled={true}/>)
        expect(getByTestId("global-loader")).toBeTruthy()
    })

    it("should hide loader if enabled prop is set to false", () => {
        const { queryByTestId } = render(<GlobalLoader enabled={false}/>)
        expect(queryByTestId("global-loader")).toBeFalsy()
    })

})
