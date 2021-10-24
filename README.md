# Dropbox Camera Upload Organiser

A command line tool to move all files from one folder to another in Dropbox.
I use this to move files from my Camera Uploads folder to sub folders prefixed
with the last modification date (`YYYY-MM`) of the file.

## Usage

```bash
export DROPBOX_API_ACCESS_TOKEN="sl..."
./camera-uploads "/Camera Uploads" "/Pictures"
```

This will move all files in the `/Camera Uploads` folder to the `/Pictures/YYYY-MM/` folder. 

For example `/Camera Uploads/hello.jpg` will be moved to `/Pictures/2021-05/hello.jpg`.

## Generating an API Access Token

Follow the [Dropbox OAuth Guide](https://developers.dropbox.com/oauth-guide).
