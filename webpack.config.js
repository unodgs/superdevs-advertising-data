const webpack = require('webpack');
const path = require('path');
const fs = require('fs');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

const dotenv = require('dotenv').config({
    path: __dirname + '/.env'
});

const makeConfig = productionMode => ({
    devtool: "source-map",
    entry: {
        main: './web/index.tsx'
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js"]
    },
    output: {
        path: path.join(__dirname, '/dist'),
        filename: productionMode ? '[name].[contenthash].js' : '[name].js',
        publicPath: '/'
    },
    optimization: {
        runtimeChunk: 'single',
        splitChunks: {
            cacheGroups: {
                vendor: {
                    test: /[\\/]node_modules[\\/]/,
                    name: 'vendors',
                    chunks: 'all',
                }
            }
        }
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './web/index.ejs',
            filename: 'index.html',
            inject: true
        }),
        new webpack.DefinePlugin({
            env: JSON.stringify(dotenv.parsed)
        })
    ],
    module: {
        rules: [{
                test: /\.ts(x?)$/,
                exclude: /node_modules/,
                use: [{
                    loader: "ts-loader"
                }]
            },
            {
                enforce: "pre",
                test: /\.js$/,
                loader: "source-map-loader"
            },
            {
                test: /\.css$/i,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.scss$/i,
                use: ["style-loader", "css-loader", "sass-loader"],
            },
            {
                test: /.(ttf|otf|eot|svg|woff(2)?)(\?[a-z0-9]+)?$/,
                use: [{
                    loader: 'file-loader'
                }]
            },
        ]
    },
    devServer: {
        contentBase: false,
        disableHostCheck: true,
        historyApiFallback: true,
        proxy: {
            '/advertising': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                logLevel: 'debug'
            }
        }
    }
})

module.exports = (env, argv) => {
    const productionMode = argv.mode === 'production';
    const config = makeConfig(productionMode);
    if (productionMode) {
        config.plugins = [new CleanWebpackPlugin(), ...config.plugins];
    }
    return config;
};