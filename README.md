# Android GitHub Repository Viewer

## Overview
This project is an Android application built to interact with the GitHub API. It allows users to view a list of repositories for a specific GitHub user or organization. The app follows the MVVM architecture and uses Kotlin Coroutines for asynchronous tasks.

## Features
- **List of Repositories**: 
    - Fetch and display a list of repositories for a specified GitHub user or organization.
    - Each repository displays:
      - Repository name
      - Description
      - Programming language
      - Number of stars
      - Number of forks
- **Pagination**: 
    - Load more repositories as the user scrolls down.
- **Error Handling**:
    - Graceful handling of potential issues such as API rate limits or network errors.
    - Appropriate error messages are shown to the user in case of failures.

## API Details
- **API Endpoint**: 
    - `https://api.github.com/users/{username}/repos`
    - Example request: `https://api.github.com/users/google/repos`
  
## Technical Requirements
- **Architecture**: MVVM (Model-View-ViewModel)
- **Coroutines**: Kotlin Coroutines for handling asynchronous tasks.
  
## Libraries and Tools Used
- Kotlin
- Coroutines
- Retrofit (for API calls)
- Gson (for JSON parsing)
- RecyclerView (for displaying the list)
  
## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/repository-name.git
